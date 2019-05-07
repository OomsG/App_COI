package be.kdg.cityofideas.model.ideations.ideaObjects

import java.io.Serializable

data class IdeaObject(
    val IdeaObjectId: Int,
    val OrderNr: Int?,
    val ImageName: String?,
    val ImagePath: String?,
    val Text: String?,
    val Url: String?
) : Serializable
