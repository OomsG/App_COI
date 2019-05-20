package be.kdg.cityofideas.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val UserId: String,
    val UserName: String,
    val Email: String,
    var Surname: String?,
    var Name: String?,
    var Sex: String?,
    var Age: Int?,
    var Zipcode: String?
)