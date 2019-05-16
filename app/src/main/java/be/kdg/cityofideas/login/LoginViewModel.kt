package be.kdg.cityofideas.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Patterns
import be.kdg.cityofideas.R
import be.kdg.cityofideas.login.data.LoginRepository
import be.kdg.cityofideas.login.data.Result
import be.kdg.cityofideas.model.users.User

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(loggedInUser: User) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(loggedInUser)

        if (result is Result.Success) {
            _loginResult.value = LoginResult(
                success = LoggedInUserView(
                    UserName = result.data.UserName,
                    Email = result.data.Email,
                    Surname = result.data.Surname,
                    Name = result.data.Name,
                    Sex = result.data.Sex,
                    Age = result.data.Age,
                    Zipcode = result.data.Zipcode
                )
            )
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
