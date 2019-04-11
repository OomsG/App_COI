package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.Users.Users

data class Votes(
    private val VoteId: Int,
    private val Confirmed: Boolean,
    private val VoteType: VoteTypes,
    private val User: Users,
    private val Idea: Ideas
)