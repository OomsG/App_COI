package be.kdg.cityofideas.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val UserName: String,
    val Email: String,
    val Surname: String?,
    val Name: String?,
    val Sex: String?,
    val Age: Int?,
    val Zipcode: String?
)
