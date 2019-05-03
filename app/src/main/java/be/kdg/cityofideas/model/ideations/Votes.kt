package be.kdg.cityofideas.model.ideations

import java.io.Serializable

data class Votes(
     val VoteId: Int,
     val Confirmed: Boolean,
     val VoteType: VoteTypes,
     val Idea: Ideas
):Serializable