package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.Users.LoggedInUsers
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.ideations.Likes

data class Reactions(
    private val ReactionId: Int,
    private val ReactionText: String,
    private val Ideation: Ideations,
    private val Idea: Ideas,
    private val Like : Collection<Likes>,
    private val LoggedInUser: LoggedInUsers
)