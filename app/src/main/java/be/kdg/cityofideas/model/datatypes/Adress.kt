package be.kdg.cityofideas.model.datatypes

data class Adress(
    private val AdressId: Int,
    private val Street:String,
    private val HouseNr:Int,
    private val City:String,
    private val Zipcode: String
)