package be.kdg.cityofideas.model.ideations

import java.io.Serializable

data class Reaction(
    val ReactionId: Int,
    val ReactionText: String?,
    val Reported: Boolean?,
    val ideation: Ideation?,
    val Idea: Idea?,
    val Likes: Collection<Like>?
) : Serializable