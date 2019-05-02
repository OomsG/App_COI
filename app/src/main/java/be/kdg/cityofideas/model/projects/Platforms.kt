package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.datatypes.Address

data class Platforms(
    val PlatformId: Int,
    val PlatformName: String,
    val Logo: String?,
    val Header: String?,
    val address: Address,
    val PhoneNumber: String?,
    val Description: String,
    val Projects: Collection<Projects>
)