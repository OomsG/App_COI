package be.kdg.cityofideas.model.ideations.IdeaObjects

import be.kdg.cityofideas.model.ideations.Ideas

abstract class IdeaObject() {
    abstract val IdeaObjectid: Int
    abstract val OrderNr: Int
    abstract val Idea: Ideas
}