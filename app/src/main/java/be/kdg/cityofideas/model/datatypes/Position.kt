package be.kdg.cityofideas.model.datatypes

import java.io.Serializable

data class Position(
    val PositionsId: Int,
    val Longitude: Double?,
    val Latitude: Double?
) : Serializable