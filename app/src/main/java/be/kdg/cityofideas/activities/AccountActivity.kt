package be.kdg.cityofideas.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import be.kdg.cityofideas.R
import be.kdg.cityofideas.login.loggedInUser

class AccountActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etSurname: EditText
    private lateinit var etLastName: EditText
    private lateinit var spinSex: Spinner
    private lateinit var etAge: EditText
    private lateinit var etZipCode: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        initialiseViews()
        addEventHandlers()
    }

    private fun initialiseViews() {
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etSurname = findViewById(R.id.etSurname)
        etLastName = findViewById(R.id.etLastName)
        spinSex = findViewById(R.id.spinSex)
        etAge = findViewById(R.id.etAge)
        etZipCode = findViewById(R.id.etZipCode)
        btnSave = findViewById(R.id.btnSaveAccountInfo)

        etUsername.setText(loggedInUser?.UserName)
        etEmail.setText(loggedInUser?.Email)
        etSurname.setText(loggedInUser?.Surname)
        etLastName.setText(loggedInUser?.Name)

        when(loggedInUser?.Sex?.toUpperCase()) {
            "MALE" -> spinSex.setSelection(1)
            "FEMALE" -> spinSex.setSelection(2)
            "OTHER" -> spinSex.setSelection(3)
            else -> spinSex.setSelection(0)
        }

        etAge.setText(loggedInUser?.Age.toString())
        etZipCode.setText(loggedInUser?.Zipcode)
    }

    private fun addEventHandlers() {
        btnSave.setOnClickListener {
            // TODO: save account info


        }
    }
}
