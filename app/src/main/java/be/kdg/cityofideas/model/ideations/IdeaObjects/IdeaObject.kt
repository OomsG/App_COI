package be.kdg.cityofideas.model.ideations.IdeaObjects

import java.io.Serializable

class IdeaObject(
    val IdeaObjectId: Int,
    val OrderNr: Int,
    val ImageName: String,
    val ImagePath: String,
    val Text: String,
    val Url: String
):Serializable
