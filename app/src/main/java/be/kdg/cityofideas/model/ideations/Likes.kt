package be.kdg.cityofideas.model.ideations

import java.io.Serializable

data class Likes(
    val LikeId: Int,
    val Reaction: Reactions
):Serializable