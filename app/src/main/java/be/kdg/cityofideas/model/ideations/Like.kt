package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.users.User
import java.io.Serializable

data class Like(
    val LikeId: Int,
    val Reaction: Reaction?,
    val User: User?
) : Serializable