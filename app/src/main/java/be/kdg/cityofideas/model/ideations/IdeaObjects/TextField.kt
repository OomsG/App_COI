package be.kdg.cityofideas.model.ideations.IdeaObjects

import be.kdg.cityofideas.model.ideations.Ideas

data class TextField (
    override val IdeaObjectid: Int,
    override val OrderNr: Int,
    override val Idea: Ideas,
    val Text: String
):IdeaObject()