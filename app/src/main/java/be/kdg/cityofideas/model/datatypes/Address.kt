package be.kdg.cityofideas.model.datatypes

data class Address(
    private val AddressId: Int,
    private val Street: String,
    private val HouseNr: Int,
    private val City: String,
    private val ZipCode: String
)