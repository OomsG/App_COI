package be.kdg.cityofideas.model.datatypes

import java.io.Serializable

data class Location(
    val LocationId: Int,
    val LocationName: String?,
    val Address: Address?,
    val Position: Position?
) : Serializable