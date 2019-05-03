package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Votes
import be.kdg.cityofideas.model.projects.Platforms

data class Users(
    val Id: String,
    val Name: String,
    val Email: String,
    val Platform: Platforms,
    val PasswordHash: String,
    val Vote: Collection<Votes>

)