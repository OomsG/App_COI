package be.kdg.cityofideas.model.users

import be.kdg.cityofideas.model.ideations.Vote
import be.kdg.cityofideas.model.projects.Platform
import java.io.Serializable

data class User(
    val Id: String,
    val UserName: String,
    val Email: String,
    val Platform: Platform?,
    val PasswordHash: String,
    var Surname: String?,
    var Name: String?,
    var Sex: String?,
    var Age: Int?,
    var Zipcode: String?,
    val Votes: Collection<Vote>?
) : Serializable