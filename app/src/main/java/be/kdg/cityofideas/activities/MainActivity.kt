package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.Users.Users
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var login: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var noAccount: TextView
    private var registering = false
    private var user: Users? = null

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
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun addEventHandlers() {
        login.setOnClickListener {
//            if (!registering) {
//                RestClient(this).getUser("users/" + email.text.toString() + "/" + password.text.toString())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.newThread())
//                    .subscribe{
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

            }
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
//    }
}
