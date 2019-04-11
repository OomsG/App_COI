package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.Users.Users
import be.kdg.cityofideas.model.datatypes.Adress

data class Platforms(
    private val PlatformId :Int,
    private val PlatformName:String,
    private val Logo :String,
    private val Header:String,
    private val Address:Adress,
    private val PhoneNumber: String,
    private val Description: String,
    private val Projects: Collection<Projects>,
    private val Users: Collection<Users>
)