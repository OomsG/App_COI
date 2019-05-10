package be.kdg.cityofideas.model.ideations

import android.graphics.Bitmap

data class IdeaObject(
    val IdeaObjectId : Int,
    val OrderNr: Int,
    val ImageName:String?,
    val ImagePath: String?,
    var Image: Bitmap?,
    val Url: String?,
    val Text:String?

)