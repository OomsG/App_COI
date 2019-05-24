package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.users.User
import java.io.Serializable

data class Vote(
    val VoteId: Int,
    val Confirmed: String,
    val VoteType: VoteType?,
    val User: User?,
    val Idea: Idea?
) : Serializable