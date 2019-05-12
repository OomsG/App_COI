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
    val Surname: String?,
    val Name: String?,
    val Sex: String?,
    val Age: Int?,
    val Zipcode: String?,
    val Votes: Collection<Vote>?
) : Serializable