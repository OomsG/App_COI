package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Votes

data class Users(
    val UserId: Int,
    val Email: String,
    val Vote: Collection<Votes>

)