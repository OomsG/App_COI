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

    fun login(loggedInUser: User, context: Context) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(loggedInUser, context)

        if (result is Result.Success) {
            _loginResult.value = LoginResult(
                success = LoggedInUserView(
                    UserId = result.data.Id,
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

    fun loginDataChanged(registering: Boolean, username: String, email: String?, password: String, confirmPwd: String?) {
        if (!registering) {
            if (!isUserNameValid(registering, username)) {
                _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
            } else if (!isPasswordValid(password)) {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            } else {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        } else {
            if (!isUserNameValid(registering, username)) {
                _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            } else if (!isEmailValid(email!!)) {
                _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            } else if (!isPasswordValid(password)) {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            } else if (!isConfirmPasswordValid(password, confirmPwd!!)) {
                _loginForm.value = LoginFormState(confirmPwdError = R.string.invalid_confirm_password)
            } else {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(registering: Boolean, username: String): Boolean {
        return if (!registering) {
            if (username.contains('@')) {
                Patterns.EMAIL_ADDRESS.matcher(username).matches()
            } else {
                username.isNotBlank()
            }
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder email validation check
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.matches("""((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#${'$'}%^&*()_+=\[{\]};:<>|./?,\-]).{6,100})""".toRegex())
    }

    // A placeholder confirm password validation check
    private fun isConfirmPasswordValid(password: String, confirmPwd: String): Boolean {
        return confirmPwd == password
    }
}
