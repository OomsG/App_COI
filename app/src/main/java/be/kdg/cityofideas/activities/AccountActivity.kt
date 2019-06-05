package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.database.DatabaseManager
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.users.Gender
import be.kdg.cityofideas.rest.RestClient

class AccountActivity : AppCompatActivity() {
    private val manager = DatabaseManager(this)
    private val helper = manager.dbHelper

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

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home)
            this.finish()
        return super.onOptionsItemSelected(item)
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

        when (loggedInUser?.Sex?.toUpperCase()) {
            Gender.MALE.name -> spinSex.setSelection(1)
            Gender.FEMALE.name -> spinSex.setSelection(2)
            Gender.OTHER.name -> spinSex.setSelection(3)
            else -> spinSex.setSelection(0)
        }

        etAge.setText(loggedInUser?.Age.toString())
        etZipCode.setText(loggedInUser?.Zipcode)
    }

    @SuppressLint("ShowToast")
    private fun addEventHandlers() {
        btnSave.setOnClickListener {
            loggedInUser?.Surname = etSurname.text.toString()
            loggedInUser?.Name = etLastName.text.toString()

            when (spinSex.selectedItem) {
                Gender.MALE.genderNL -> loggedInUser?.Sex = Gender.MALE.genderEN
                Gender.FEMALE.genderNL -> loggedInUser?.Sex = Gender.FEMALE.genderEN
                Gender.OTHER.genderNL -> loggedInUser?.Sex = Gender.OTHER.genderEN
                else -> loggedInUser?.Sex = null
            }

            if (etAge.text.toString().isBlank())
                loggedInUser?.Age = 0
            else
                loggedInUser?.Age = etAge.text.toString().toInt()

            loggedInUser?.Zipcode = etZipCode.text.toString()

            saveAccountInfoToBackEnd()

            Toast.makeText(applicationContext, "Info opgeslagen!", Toast.LENGTH_LONG)
        }
    }

    private fun saveAccountInfoToBackEnd() {
        manager.openDatabase()

        manager.update(
            helper.getUserEntry().TBL_USER,
            ContentValues().apply {
                put(helper.getUserEntry().USER_SURNAME, loggedInUser?.Surname)
                put(helper.getUserEntry().USER_LAST_NAME, loggedInUser?.Name)
                put(helper.getUserEntry().USER_SEX, loggedInUser?.Sex)
                put(helper.getUserEntry().USER_AGE, loggedInUser?.Age)
                put(helper.getUserEntry().USER_ZIP, loggedInUser?.Zipcode)
            },
            "${helper.getUserEntry().USER_NAME} = ?",
            arrayOf(loggedInUser?.UserName.toString())
        )

        loggedInUser?.let {
            Thread {
                RestClient(this).updateUser(it)
            }.start()
        }
    }
}
