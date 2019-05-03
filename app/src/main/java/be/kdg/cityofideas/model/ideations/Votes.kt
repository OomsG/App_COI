package be.kdg.cityofideas.model.ideations

data class Votes(
     val VoteId: Int,
     val Confirmed: Boolean,
     val VoteType: VoteTypes,
     val Idea: Ideas
)