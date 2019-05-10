package be.kdg.cityofideas.model.users

import be.kdg.cityofideas.model.ideations.Vote
import be.kdg.cityofideas.model.projects.Platform

data class User(
    val Id: String,
    val Name: String,
    val Email: String,
    val Platform: Platform?,
    val PasswordHash: String,
    val Votes: Collection<Vote>?
)