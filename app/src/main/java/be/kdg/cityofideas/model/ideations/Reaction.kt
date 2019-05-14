package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.users.User
import java.io.Serializable

data class Reaction(
    val ReactionId: Int,
    val ReactionText: String?,
    val Reported: Boolean?,
    val User: User?,
    val Ideation: Ideation?,
    val Idea: Idea?,
    val Likes: Collection<Like>?
) : Serializable