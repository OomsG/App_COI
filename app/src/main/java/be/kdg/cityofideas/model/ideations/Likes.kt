package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.Users.LoggedInUsers

data class Likes(
    private val LikeId: Int,
    private val Reaction: Reactions,
    private val LoggedInUser: LoggedInUsers
)