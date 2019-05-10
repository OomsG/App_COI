package be.kdg.cityofideas.model.ideations

import java.io.Serializable

data class Vote(
    val VoteId: Int,
    val Confirmed: Boolean?,
    val VoteType: VoteType?,
    val Idea: Idea?
) : Serializable