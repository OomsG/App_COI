package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.database.DatabaseManager
import be.kdg.cityofideas.model.datatypes.Location
import be.kdg.cityofideas.model.users.User
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var login: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var noAccount: TextView
    private var registering = false
    private var user: User? = null

    private val manager = DatabaseManager(this)
    private val helper = manager.dbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseViews()
        addEventHandlers()
       // initialiseDatabase()
    }

    override fun onDestroy() {
        manager.closeDatabase()
        super.onDestroy()
    }

    override fun onDestroy() {
        manager.closeDatabase()
        super.onDestroy()
    }

    private fun initialiseViews() {
        login = findViewById(R.id.Login)
        email = findViewById(R.id.EmailText)
        password = findViewById(R.id.PasswoordText)
        noAccount = findViewById(R.id.tvCreateAccount)
    }

    @SuppressLint("CheckResult")
    private fun initialiseDatabase() {
        manager.openDatabase()

        //region RestClient getUsers
        RestClient(this)
            .getUsers("users")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    //region insert User
                    manager.insert(
                        helper.getUserEntry().TBL_USER,
                        helper.getUserContentValues(
                            it.Id,
                            it.Email,
                            it.Name,
                            it.PasswordHash
                        )
                    )
                    //endregion
                }
            }
        //endregion

        //region RestClient getProjects
        RestClient(this)
            .getProjects("projects/" + 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    val projectId = it.ProjectId

                    //region insert Project
                    manager.insert(
                        helper.getProjectEntry().TBL_PROJECT,
                        helper.getProjectContentValues(
                            it.ProjectId,
                            it.ProjectName,
                            it.StartDate,
                            it.EndDate,
                            it.Objective,
                            it.Description,
                            it.Status
                        )
                    )
                    //endregion

                    //region insert Location
                    it.Location?.let {
                        val locationId = it.LocationId

                        // insert Location
                        manager.insert(
                            helper.getLocationEntry().TBL_LOCATION,
                            helper.getLocationContentValues(
                                it.LocationId,
                                it.LocationName
                            )
                        )

                        // update Project with LocationId
                        manager.update(
                            helper.getProjectEntry().TBL_PROJECT,
                            ContentValues().apply {
                                put(helper.getLocationEntry().LOCATION_ID, it.LocationId)
                            },
                            "${helper.getProjectEntry().PROJECT_ID} = ?",
                            arrayOf(projectId.toString())
                        )

                        //region Address
                        it.Address?.let {
                            // insert Address
                            manager.insert(
                                helper.getAddressEntry().TBL_ADDRESS,
                                helper.getAddressContentValues(
                                    it.AddressId,
                                    it.Street,
                                    it.HouseNr,
                                    it.City,
                                    it.ZipCode
                                )
                            )

                            // update Location with Address
                            manager.update(
                                helper.getLocationEntry().TBL_LOCATION,
                                ContentValues().apply {
                                    put(helper.getAddressEntry().ADDRESS_ID, it.AddressId)
                                },
                                "${helper.getLocationEntry().LOCATION_ID} = ?",
                                arrayOf(locationId.toString())
                            )
                        }
                        //endregion

                        //region Position
                        it.Position?.let {
                            // insert Position
                            manager.insert(
                                helper.getPositionEntry().TBL_POSITION,
                                helper.getPositionContentValues(
                                    it.PositionId,
                                    it.Longitude,
                                    it.Latitude
                                )
                            )

                            // update Location with Position
                            manager.update(
                                helper.getLocationEntry().TBL_LOCATION,
                                ContentValues().apply {
                                    put(helper.getPositionEntry().POSITION_ID, it.PositionId)
                                },
                                "${helper.getLocationEntry().LOCATION_ID} = ?",
                                arrayOf(locationId.toString())
                            )
                        }
                        //endregion
                    }
                    //endregion

                    it.Phases?.forEach {
                        val phaseId = it.PhaseId

                        //region insert Phase
                        manager.insert(
                            helper.getPhaseEntry().TBL_PHASE,
                            helper.getPhaseContentValues(
                                it.PhaseId,
                                it.PhaseName,
                                it.PhaseNr,
                                it.Description,
                                it.StartDate,
                                it.EndDate,
                                projectId
                            )
                        )
                        //endregion

                        it.Ideations?.forEach {
                            val ideationId = it.IdeationId

                            //region insert Ideation
                            manager.insert(
                                helper.getIdeationEntry().TBL_IDEATION,
                                helper.getIdeationContentValues(
                                    it.IdeationId,
                                    it.CentralQuestion,
                                    it.InputIdeation,
                                    phaseId
                                )
                            )
                            //endregion

                            it.Reactions?.forEach {
                                val reactionId = it.ReactionId

                                //region insert Reaction (Ideations)
                                // insert Reaction
                                manager.insert(
                                    helper.getReactionEntry().TBL_REACTION,
                                    helper.getReactionContentValues(
                                        it.ReactionId,
                                        it.ReactionText,
                                        it.Reported
                                    )
                                )

                                //update Reaction with IdeationId
                                manager.update(
                                    helper.getReactionEntry().TBL_REACTION,
                                    ContentValues().apply {
                                        put(helper.getIdeationEntry().IDEATION_ID, ideationId)
                                    },
                                    "${helper.getReactionEntry().REACTION_ID} = ?",
                                    arrayOf(reactionId.toString())
                                )
                                //endregion

                                it.Likes?.forEach {
                                    //region insert Like
//                                    manager.insert(
//
//                                    )
                                    //endregion
                                }
                            }

                            it.Ideas?.forEach {
                                val ideaId = it.IdeaId

                                //region insert Idea
                                manager.insert(
                                    helper.getIdeaEntry().TBL_IDEA,
                                    helper.getIdeaContentValues(
                                        it.IdeaId,
                                        it.Reported,
                                        it.Title,
                                        ideationId
                                    )
                                )
                                //endregion

                                it.IdeaObjects?.forEach {
                                    //region insert IdeaObject
                                    //endregion
                                }

                                it.Votes?.forEach {
                                    //region insert Vote
                                    manager.insert(
                                        helper.getVoteEntry().TBL_VOTE,
                                        helper.getVoteContentValues(
                                            it.VoteId,
                                            it.Confirmed,
                                            it.VoteType?.ordinal,
                                            ideaId
                                        )
                                    )
                                    //endregion
                                }

                                it.Reactions?.forEach {
                                    //region insert Reaction (Ideas)
                                    // insert Reaction
                                    manager.insert(
                                        helper.getReactionEntry().TBL_REACTION,
                                        helper.getReactionContentValues(
                                            it.ReactionId,
                                            it.ReactionText,
                                            it.Reported
                                        )
                                    )

                                    //update Reaction with IdeaId
                                    manager.update(
                                        helper.getReactionEntry().TBL_REACTION,
                                        ContentValues().apply {
                                            put(helper.getIdeaEntry().IDEA_ID, ideaId)
                                        },
                                        "${helper.getReactionEntry().REACTION_ID} = ?",
                                        arrayOf(it.ReactionId.toString())
                                    )
                                    //endregion

                                    it.Likes?.forEach {
                                        //region insert Like
//                                    manager.insert(
//
//                                    )
                                        //endregion
                                    }
                                }
                            }
                        }

                        it.Surveys?.forEach {
                            val surveyId = it.SurveyId

                            //region insert Survey
                            manager.insert(
                                helper.getSurveyEntry().TBL_SURVEY,
                                helper.getSurveyContentValues(
                                    it.SurveyId,
                                    it.Title,
                                    phaseId
                                )
                            )
                            //endregion

                            it.Questions?.forEach {
                                val questionId = it.QuestionId

                                //region insert Question
                                manager.insert(
                                    helper.getQuestionEntry().TBL_QUESTION,
                                    helper.getQuestionContentValues(
                                        it.QuestionId,
                                        it.QuestionNr,
                                        it.QuestionText,
                                        it.QuestionType?.ordinal,
                                        surveyId
                                    )
                                )
                                //endregion

                                it.Answers?.forEach {
                                    //region insert Answer
                                    manager.insert(
                                        helper.getAnswerEntry().TBL_ANSWER,
                                        helper.getAnswerContentValues(
                                            it.AnswerId,
                                            it.AnswerText,
                                            it.TotalTimesChosen,
                                            questionId
                                        )
                                    )
                                    //endregion
                                }
                            }
                        }
                    }
                }
            }
        //endregion
    }


    @SuppressLint("SetTextI18n", "CheckResult")
    private fun addEventHandlers() {
        login.setOnClickListener {
            //TESTING database
            /*try {
                 val contentValues = ContentValues().apply {
                     put(helper.USER_ID, 10)
                     put(helper.USER_EMAIL, email.text.toString())
                     put(helper.USER_PASSWORD, password.text.toString())
                 }
                 manager.openDatabase()
                 val inserted = manager.insert(helper.TBL_USER, contentValues)

                 if (inserted) {
                     Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                 } else {
                     Toast.makeText(this, "this user already exists", Toast.LENGTH_SHORT).show()
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
             }*/

            if (!registering) {
//                RestClient(this).getUser("users/" + email.text.toString() + "/" + password.text.toString())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.newThread())
//                    .subscribe {
//                        user = it
//                    }
//
//                Thread.sleep(1000)

//                val result = manager.getDetails(
//                    "SELECT * FROM " + helper.TBL_USER +
//                            " WHERE " + helper.USER_EMAIL + " = " + email.text.toString()
//                )
//
//                val args = arrayOf(email.text.toString())
//
////                manager.openDatabase()
//
//                val query = manager.getDetails(
//                    helper.TBL_USER,
//                    null,
//                    "${helper.USER_EMAIL} = ?",
//                    args,
//                    null,
//                    null,
//                    null
//                )
//
//                with(query) {
//                    query.moveToFirst()
//                    user = User(
//                        getString(getColumnIndexOrThrow(helper.USER_ID)),
//                        getString(getColumnIndexOrThrow(helper.USER_NAME)),
//                        getString(getColumnIndexOrThrow(helper.USER_EMAIL)),
//                        null,
//                        getString(getColumnIndexOrThrow(helper.USER_PASSWORD)),
//                        null
//                    )
//                }
//
//                Log.d("user", user.toString())

//                manager.closeDatabase()

//                result?.moveToFirst()
//                user = User(
//                    result!!.getString(0),
//                    result.getString(1),
//                    result.getString(2),
//                    null,
//                    result.getString(3),
//                    null
//                )

//                if (user != null) {
                val intent = Intent(it.context, ProjectsActivity::class.java)
                startActivity(intent)
//                } else {
//                    Toast.makeText(this, "Foutieve login, probeer opnieuw...", Toast.LENGTH_SHORT).show()
//                }
            } else {

            }
        }

        noAccount.setOnClickListener {
            if (!registering) {
                login.text = "Register"
                noAccount.text = "Al een account? Log in!"
                registering = true
            } else {
                login.text = getString(R.string.log_in)
                noAccount.text = getString(R.string.no_account)
                registering = false
            }
        }
    }
}
