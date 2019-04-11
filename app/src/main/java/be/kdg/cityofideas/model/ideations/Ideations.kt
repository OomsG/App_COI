package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.projects.Phases

data class Ideations(
    private val IdeationId: Int,
    private val CentralQuestion: String,
    private val InputIdeation: Boolean,
    private val Phase: Collection<Phases>,
    private val Reactions: Collection<Reactions>,
    private val Ideas: Collection<Ideas>
)