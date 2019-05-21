package be.kdg.cityofideas.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPwdError: Int? = null,
    val isDataValid: Boolean = false
)
