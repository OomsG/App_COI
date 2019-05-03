package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Votes
import be.kdg.cityofideas.model.projects.Platforms

data class Users(
    val UserId: String,
    val Name: String,
    val Email: String,
    val Votes: Collection<Votes>,
    val Platform: Platforms
)