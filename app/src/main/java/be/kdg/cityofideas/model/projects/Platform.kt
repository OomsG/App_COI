package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.datatypes.Address
import java.io.Serializable

data class Platform(
    val PlatformId: Int,
    val PlatformName: String?,
    val Logo: String?,
    val Header: String?,
    val Address: Address?,
    val PhoneNumber: String?,
    val Description: String?,
    val Projects: Collection<Project>?
) : Serializable