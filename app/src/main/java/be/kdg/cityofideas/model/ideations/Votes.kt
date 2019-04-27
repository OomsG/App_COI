package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.Users.Users

data class Votes(
     val VoteId: Int,
     val Confirmed: Boolean,
     val VoteType: VoteTypes,
     val Idea: Ideas
)