package be.kdg.cityofideas.model.datatypes

import java.io.Serializable

data class Position(
    val PositionId: Int,
    val Longitude: String?,
    val Latitude: String?
) : Serializable