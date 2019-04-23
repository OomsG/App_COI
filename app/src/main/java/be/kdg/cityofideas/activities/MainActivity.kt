package be.kdg.cityofideas.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import be.kdg.cityofideas.R

class MainActivity : AppCompatActivity() {
    private lateinit var login: Button
    private lateinit var email:EditText
    private lateinit var passwoord:EditText
    private lateinit var register:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseViews()

    }

    fun initialiseViews(){
        login = findViewById(R.id.Login)
        login.setOnClickListener {
            val intent = Intent(it.context, ProjectsActivity::class.java)
            startActivity(intent)
        }
        email = findViewById(R.id.EmailText)
        passwoord = findViewById(R.id.PasswoordText)
    }



}
