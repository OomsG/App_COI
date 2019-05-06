package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.database.DatabaseManager
import be.kdg.cityofideas.fragments.PLATFORM_ID
import be.kdg.cityofideas.model.Users.Users
import be.kdg.cityofideas.model.projects.Projects
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var login: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var noAccount: TextView
    private var registering = false
    private var user: Users? = null

    private val manager = DatabaseManager(this)
    private val helper = manager.dbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseViews()
        addEventHandlers()
    }

    private fun initialiseViews() {
        login = findViewById(R.id.Login)
        email = findViewById(R.id.EmailText)
        password = findViewById(R.id.PasswoordText)
        noAccount = findViewById(R.id.tvCreateAccount)
//        initialiseDatabase()
    }

    @SuppressLint("CheckResult")
    private fun initialiseDatabase() {
        RestClient(this)
            .getProjects("projects/" + 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.forEach {
                    val projectId = it.ProjectId
                    //region Project
                    try {
                        val contentValues = ContentValues().apply {
                            put(helper.PROJECT_ID, it.ProjectId)
                            put(helper.PROJECT_NAME, it.ProjectName)
                            put(helper.PROJECT_STARTDATE, it.StartDate)
                            put(helper.PROJECT_ENDDATE, it.EndDate)
                            put(helper.PROJECT_OBJECTIVE, it.Objective)
                            put(helper.PROJECT_DESCRIPTION, it.Description)
                            put(helper.PROJECT_STATUS, it.Status)
                        }
                        manager.openDatabase()
                        manager.insert(helper.TBL_PROJECT, contentValues)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } //endregion
                    //region Location
                    try {
                        val contentValues = ContentValues().apply {

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //endregion
                    it.Phases.forEach {
                        //region Phases
                        val phaseId = it.PhaseId
                        try {
                            val contentValues = ContentValues().apply {
                                put(helper.PHASE_ID, it.PhaseId)
                                put(helper.PHASE_NAME, it.PhaseName)
                                put(helper.PHASE_NR, it.PhaseNr)
                                put(helper.PHASE_DESCRIPTION, it.Description)
                                put(helper.PHASE_STARTDATE, it.StartDate)
                                put(helper.PHASE_ENDDATE, it.EndDate)
                                put(helper.PROJECT_ID, projectId)
                            }
                            manager.openDatabase()
                            manager.insert(helper.TBL_PHASE, contentValues)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        //endregion
                        it.Ideations.forEach {
                            //region Ideation
                            try {
                                val contentValues = ContentValues().apply {
                                    put(helper.IDEATION_ID, it.IdeationId)
                                    put(helper.IDEATION_CENTRALQUESTION, it.CentralQuestion)
                                    put(helper.IDEATION_INPUTIDEATION, it.InputIdeation)
                                    put(helper.PHASE_ID, phaseId)
                                }
                                manager.openDatabase()
                                manager.insert(helper.TBL_IDEATION, contentValues)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            //endregion

                            it.Ideas.forEach {
                                try {
                                    Log.d("help", it.IdeaId.toString())
                                    val contentValues = ContentValues().apply {
                                        put(helper.IDEA_ID, it.IdeaId)
                                        put(helper.IDEA_REPORTED, it.Reported)
                                        put(helper.IDEA_TITLE, it.Title)
                                    }
                                    manager.insert(helper.TBL_IDEA, contentValues)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }

                        it.Surveys.forEach {

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

//            if (!registering) {
//                RestClient(this).getUser("users/" + email.text.toString() + "/" + password.text.toString())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.newThread())
//                    .subscribe {
//                        user = it
//                    }
//
//                Thread.sleep(1000)
//
//                if (user != null) {
            val intent = Intent(it.context, ProjectsActivity::class.java)
            startActivity(intent)
//                } else {
//                    Toast.makeText(this, "Foutieve login, probeer opnieuw...", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//
//            }
        }

//        noAccount.setOnClickListener {
//            if (!registering) {
//                login.text = "Register"
//                noAccount.text = "Al een account? Log in!"
//                registering = true
//            } else {
//                login.text = getString(R.string.log_in)
//                noAccount.text = getString(R.string.no_account)
//                registering = false
//            }
//        }
    }
}
