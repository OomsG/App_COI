package be.kdg.cityofideas

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.users.User

// SharedPref file name
const val PREF_NAME = "AutoLogin"
// All Shared Preferences Keys
const val IS_LOGIN = "IsLoggedIn"
const val KEY_USER_NAME = "UserName"
const val KEY_USER_EMAIL = "Email"
const val KEY_USER_SURNAME = "SurName"
const val KEY_USER_LASTNAME = "LastName"
const val KEY_USER_SEX = "Sex"
const val KEY_USER_AGE = "Age"
const val KEY_USER_ZIP = "ZipCode"

class SessionManager
@SuppressLint("CommitPrefEdits")
constructor(context: Context) {
    // Shared Preferences
    private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    // Editor for Shared preferences
    private var editor: SharedPreferences.Editor = pref.edit()

    /**
     * Create login session
     */
    fun createLoginSession(loggedInUser: User) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        // Storing in pref
        editor.putString(KEY_USER_NAME, loggedInUser.UserName)
        editor.putString(KEY_USER_EMAIL, loggedInUser.Email)
        editor.putString(KEY_USER_SURNAME, loggedInUser.Surname)
        editor.putString(KEY_USER_LASTNAME, loggedInUser.Name)
        editor.putString(KEY_USER_SEX, loggedInUser.Sex)
        editor.putInt(KEY_USER_AGE, loggedInUser.Age!!)
        editor.putString(KEY_USER_ZIP, loggedInUser.Zipcode)
        // commit changes
        editor.apply()
    }

    /**
     * Clear session details
     */
    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear()
        editor.commit()

        loggedInUser = null
    }
}