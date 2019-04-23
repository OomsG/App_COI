package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.Users.LoggedInUsers

data class Likes(
    val LikeId: Int,
    val Reaction: Reactions,
    val LoggedInUser: LoggedInUsers
)