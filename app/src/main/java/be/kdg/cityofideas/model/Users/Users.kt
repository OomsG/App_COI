package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Votes

data class Users(
    val Id: String,
    val Email: String,
    val PasswordHash: String,
    val Vote: Collection<Votes>

)