package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.projects.Phase
import java.io.Serializable

data class Ideation(
    val IdeationId: Int,
    val CentralQuestion: String?,
    val Phase: Phase?,
    val InputIdeation: Boolean?,
    val Reactions: Collection<Reaction>?,
    val Ideas: Collection<Idea>?,
    val Text: Boolean?,
    val Image: Boolean?,
    val Video: Boolean?,
    val Map: Boolean?,
    val TextRequired: Boolean?,
    val ImageRequired: Boolean?,
    val VideoRequired: Boolean?,
    val MapRequired: Boolean?
    ) : Serializable