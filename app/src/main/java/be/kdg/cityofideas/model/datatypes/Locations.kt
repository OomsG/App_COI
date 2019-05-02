package be.kdg.cityofideas.model.datatypes

data class Locations(
    private val LocationId: Int,
    private val LocationName: String,
    private val address: Address,
    private val Position: Positions?
)