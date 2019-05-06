package be.kdg.cityofideas.model.ideations.IdeaObjects

import android.graphics.Bitmap
import java.io.Serializable

class IdeaObject(
    val IdeaObjectId: Int,
    val OrderNr: Int,
    val ImageName: String?,
    val ImagePath: String?,
    var Image:Bitmap?,
    val Text: String?,
    val Url: String?
):Serializable
