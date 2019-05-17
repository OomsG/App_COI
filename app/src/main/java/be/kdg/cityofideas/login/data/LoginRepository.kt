package be.kdg.cityofideas.login.data

import android.content.Context
import be.kdg.cityofideas.SessionManager
import be.kdg.cityofideas.model.users.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {
    var user: User? = null
        private set

    init {
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(loggedInUser: User, context: Context): Result<User> {
        val result = dataSource.login(loggedInUser)

        if (result is Result.Success) {
            val sessionManager = SessionManager(context)

            setLoggedInUser(result.data, sessionManager)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: User, sessionManager: SessionManager) {
        this.user = loggedInUser

        sessionManager.createLoginSession(this.user!!)
    }
}
