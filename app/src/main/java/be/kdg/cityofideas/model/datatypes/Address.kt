package be.kdg.cityofideas.model.datatypes

import java.io.Serializable

data class Address(
    val AddressId: Int,
    val Street: String?,
    val HouseNr: String?,
    val City: String?,
    val ZipCode: String?
) : Serializable