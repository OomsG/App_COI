package be.kdg.cityofideas.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

// SharedPref file name
const val PREF_NAME = "AutoLogin"
// All Shared Preferences Keys
const val IS_LOGIN = "IsLoggedIn"
const val KEY_USER_ID = "UserId"

class LoginSessionManager
@SuppressLint("CommitPrefEdits")
constructor(context: Context) {
    // Shared Preferences
    private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    // Editor for Shared preferences
    private var editor: SharedPreferences.Editor = pref.edit()

    /**
     * Create login session
     */
    fun createLoginSession(loggedInUser: LoggedInUserView) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        // Storing in pref
        editor.putString(KEY_USER_ID, loggedInUser.UserId)
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