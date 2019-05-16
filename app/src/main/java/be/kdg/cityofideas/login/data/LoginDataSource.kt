package be.kdg.cityofideas.login.data

import android.annotation.SuppressLint
import be.kdg.cityofideas.model.users.User
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    @SuppressLint("CheckResult")
    fun login(loggedInUser: User): Result<User> {
        try {
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

