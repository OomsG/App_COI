package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.projects.Phases
import java.io.Serializable

data class Ideations(
    val IdeationId: Int,
    val CentralQuestion: String,
    val Phase: Phases,
    val InputIdeation: Boolean,
    val Reactions: Collection<Reactions>,
    val Ideas: Collection<Ideas>
)