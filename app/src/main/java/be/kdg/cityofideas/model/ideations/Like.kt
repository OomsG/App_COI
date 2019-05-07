package be.kdg.cityofideas.model.ideations

import java.io.Serializable

data class Like(
    val LikeId: Int,
    val Reaction: Reaction?
) : Serializable