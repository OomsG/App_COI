package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Votes

data class Users(
    private val UserId: Int,
    private val Email: String,
    private val Vote:Collection<Votes>

)