package be.kdg.cityofideas.login.data

import android.content.Context
import be.kdg.cityofideas.login.LoggedInUserView
import be.kdg.cityofideas.login.LoginSessionManager
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.users.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {
    fun login(loggedInUser: User, context: Context): Result<User> {
        val result = dataSource.login(loggedInUser)

        if (result is Result.Success) {
            val sessionManager = LoginSessionManager(context)

            setLoggedInUser(result.data, sessionManager)
        }

        return result
    }

    private fun setLoggedInUser(user: User, sessionManager: LoginSessionManager) {
        loggedInUser = LoggedInUserView(
            user.Id,
            user.UserName,
            user.Email,
            user.Surname,
            user.Name,
            user.Sex,
            user.Age,
            user.Zipcode
        )

        sessionManager.createLoginSession(loggedInUser!!)
    }
}
