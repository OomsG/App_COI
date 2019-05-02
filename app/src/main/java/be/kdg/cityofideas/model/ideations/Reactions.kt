package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.Users.LoggedInUsers
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.ideations.Likes

data class Reactions(
    val ReactionId: Int,
    val ReactionText: String,
    val Reported: Boolean,
    val Ideation: Ideations,
    val Idea: Ideas?,
    val Like: Collection<Likes>
)