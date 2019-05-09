package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.database.DatabaseManager
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
        initialiseDatabase()
    }

    private fun initialiseViews() {
        login = findViewById(R.id.Login)
        email = findViewById(R.id.EmailText)
        password = findViewById(R.id.PasswoordText)
        noAccount = findViewById(R.id.tvCreateAccount)
    }

    @SuppressLint("CheckResult")
    private fun initialiseDatabase() {
        RestClient(this)
            .getUsers("users")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    manager.insert(
                        helper.getUserTable(),
                        helper.getUserContentValues(
                            it.Id,
                            it.Email,
                            it.Name,
                            it.PasswordHash
                        )
                    )
                }
            }

        RestClient(this)
            .getProjects("projects/" + 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    val projectId = it.ProjectId

                    //region Project
                    manager.insert(
                        helper.getProjectTable(),
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

                    //region Location

                    //endregion

                    it.Phases?.forEach {
                        val phaseId = it.PhaseId

                        //region Phase
                        manager.insert(
                            helper.getPhaseTable(),
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

                            //region Ideation
                            manager.insert(
                                helper.getIdeationTable(),
                                helper.getIdeationContentValues(
                                    it.IdeationId,
                                    it.CentralQuestion,
                                    it.InputIdeation,
                                    phaseId
                                )
                            )
                            //endregion

                            it.Ideas?.forEach {
                                //region Idea
                                manager.insert(
                                    helper.getIdeaTable(),
                                    helper.getIdeaContentValues(
                                        it.IdeaId,
                                        it.Reported,
                                        it.Title,
                                        ideationId
                                    )
                                )
                                //endregion
                            }
                        }

                        it.Surveys?.forEach {

                        }
                    }
                }
            }
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
