package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import be.kdg.cityofideas.*
import be.kdg.cityofideas.database.DatabaseManager
import be.kdg.cityofideas.login.*
import be.kdg.cityofideas.model.ideations.getBytes
import be.kdg.cityofideas.model.users.User
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity() {
    private val manager = DatabaseManager(this)
    private val helper = manager.dbHelper
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialiseDatabase()

        pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        if (pref.getBoolean(IS_LOGIN, false)) {
            setLoggedInUser()
            invalidateOptionsMenu()
        }

        startProjectsActivity()
    }

    override fun onDestroy() {
        manager.closeDatabase()
        super.onDestroy()
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
                            it.UserName,
                            it.PasswordHash,
                            it.Surname,
                            it.Name,
                            it.Sex,
                            it.Age,
                            it.Zipcode
                        )
                    )
                    //endregion
                }
            }
        //endregion

        //region RestClient getTags
        RestClient(this)
            .getTags("tags")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    manager.insert(
                        helper.getTagEntry().TBL_TAG,
                        helper.getTagContentValues(
                            it.TagId,
                            it.TagName
                        )
                    )
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
                            projectId,
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
                                locationId,
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
                                phaseId,
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
                                    ideationId,
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
                                        reactionId,
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

                                // update Reaction with userId
                                it.User?.let {
                                    manager.update(
                                        helper.getReactionEntry().TBL_REACTION,
                                        ContentValues().apply {
                                            put(helper.getUserEntry().USER_ID, it.Id)
                                        },
                                        "${helper.getReactionEntry().REACTION_ID} = ?",
                                        arrayOf(reactionId.toString())
                                    )
                                }

                                it.Likes?.forEach {
                                    val likeId = it.LikeId

                                    //region insert Like
                                    manager.insert(
                                        helper.getLikeEntry().TBL_LIKE,
                                        helper.getLikeContentValues(
                                            it.LikeId,
                                            reactionId
                                        )
                                    )

                                    it.User?.let {
                                        manager.update(
                                            helper.getLikeEntry().TBL_LIKE,
                                            ContentValues().apply {
                                                put(helper.getUserEntry().USER_ID, it.Id)
                                            },
                                            "${helper.getLikeEntry().LIKE_ID} = ?",
                                            arrayOf(likeId.toString())
                                        )
                                    }
                                    //endregion
                                }
                                //endregion
                            }

                            it.Ideas?.forEach {
                                val ideaId = it.IdeaId

                                //region insert Idea
                                manager.insert(
                                    helper.getIdeaEntry().TBL_IDEA,
                                    helper.getIdeaContentValues(
                                        ideaId,
                                        it.Reported,
                                        it.Title,
                                        ideationId
                                    )
                                )

                                // update Idea with userId
                                it.User?.let {
                                    manager.update(
                                        helper.getIdeaEntry().TBL_IDEA,
                                        ContentValues().apply {
                                            put(helper.getUserEntry().USER_ID, it.Id)
                                        },
                                        "${helper.getIdeaEntry().IDEA_ID} = ?",
                                        arrayOf(ideaId.toString())
                                    )
                                }
                                //endregion

                                it.IdeaObjects?.forEach {
                                    //region insert IdeaObject
                                    manager.insert(
                                        helper.getIdeaObjectEntry().TBL_IDEA_OBJECT,
                                        helper.getIdeaObjectContentValues(
                                            it.IdeaObjectId,
                                            it.OrderNr,
                                            ideaId,
                                            it.Discriminator,
                                            it.ImageName,
                                            it.ImagePath,
                                            it.Image?.let {
                                                getBytes(it)
                                            },
                                            it.Text,
                                            it.Url
                                        )
                                    )
                                    //endregion
                                }

                                it.IdeaTags?.forEach {
                                    val ideaTagId = it.IdeaTagId

                                    //region insert IdeaTag
                                    // insert IdeaTag
                                    manager.insert(
                                        helper.getTagEntry().TBL_IDEA_TAG,
                                        helper.getIdeaTagContentValues(
                                            ideaTagId,
                                            ideaId
                                        )
                                    )

                                    // update IdeaTag with tagId
                                    it.Tag?.let {
                                        manager.update(
                                            helper.getTagEntry().TBL_IDEA_TAG,
                                            ContentValues().apply {
                                                put(helper.getTagEntry().TAG_ID, it.TagId)
                                            },
                                            "${helper.getTagEntry().IDEA_TAG_ID} = ?",
                                            arrayOf(ideaTagId.toString())
                                        )
                                    }
                                    //endregion
                                }

                                it.Votes?.forEach {
                                    val voteId = it.VoteId

                                    //region insert Vote
                                    manager.insert(
                                        helper.getVoteEntry().TBL_VOTE,
                                        helper.getVoteContentValues(
                                            voteId,
                                            it.Confirmed,
                                            it.VoteType?.ordinal,
                                            ideaId
                                        )
                                    )

                                    // update Vote with userId
                                    it.User?.let {
                                        manager.update(
                                            helper.getVoteEntry().TBL_VOTE,
                                            ContentValues().apply {
                                                put(helper.getUserEntry().USER_ID, it.Id)
                                            },
                                            "${helper.getVoteEntry().VOTE_ID} = ?",
                                            arrayOf(voteId.toString())
                                        )
                                    }
                                    //endregion
                                }

                                it.Reactions?.forEach {
                                    val reactionId = it.ReactionId

                                    //region insert Reaction (Ideas)
                                    // insert Reaction
                                    manager.insert(
                                        helper.getReactionEntry().TBL_REACTION,
                                        helper.getReactionContentValues(
                                            reactionId,
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

                                    // update Reaction with userId
                                    it.User?.let {
                                        manager.update(
                                            helper.getReactionEntry().TBL_REACTION,
                                            ContentValues().apply {
                                                put(helper.getUserEntry().USER_ID, it.Id)
                                            },
                                            "${helper.getReactionEntry().REACTION_ID} = ?",
                                            arrayOf(reactionId.toString())
                                        )
                                    }

                                    it.Likes?.forEach {
                                        val likeId = it.LikeId

                                        //region insert Like
                                        // insert Like
                                        manager.insert(
                                            helper.getLikeEntry().TBL_LIKE,
                                            helper.getLikeContentValues(
                                                it.LikeId,
                                                reactionId
                                            )
                                        )

                                        // update Like with userId
                                        it.User?.let {
                                            manager.update(
                                                helper.getLikeEntry().TBL_LIKE,
                                                ContentValues().apply {
                                                    put(helper.getUserEntry().USER_ID, it.Id)
                                                },
                                                "${helper.getLikeEntry().LIKE_ID} = ?",
                                                arrayOf(likeId.toString())
                                            )
                                        }
                                        //endregion
                                    }
                                    //endregion
                                }
                            }
                        }

                        it.Surveys?.forEach {
                            val surveyId = it.SurveyId

                            //region insert Survey
                            manager.insert(
                                helper.getSurveyEntry().TBL_SURVEY,
                                helper.getSurveyContentValues(
                                    surveyId,
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
                                        questionId,
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

    private fun startProjectsActivity() {
        val intent = Intent(this, ProjectsActivity::class.java)
        startActivity(intent)
    }

    private fun setLoggedInUser() {
            val c = manager.getDetails(
                helper.getUserEntry().TBL_USER,
                null,
                "${helper.getUserEntry().USER_ID} = ?",
                arrayOf(pref.getString(KEY_USER_ID, "")!!),
                null, null, null
            )

        if (c.moveToFirst()) {
            loggedInUser = LoggedInUserView(
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_ID)),
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_NAME)),
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_EMAIL)),
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_SURNAME)),
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_LAST_NAME)),
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_SEX)),
                c.getInt(c.getColumnIndex(helper.getUserEntry().USER_AGE)),
                c.getString(c.getColumnIndex(helper.getUserEntry().USER_ZIP))
            )
        }
        c.close()
    }
}
