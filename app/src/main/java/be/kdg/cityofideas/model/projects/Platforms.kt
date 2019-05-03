package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.datatypes.Adress

data class Platforms(
     val PlatformId :Int,
     val PlatformName:String,
     val Logo :String,
     val Header:String,
     val Address:Adress,
     val PhoneNumber: String,
     val Description: String,
     val Projects: Collection<Projects>
)