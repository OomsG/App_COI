package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.projects.Phases

data class Ideations(
    val IdeationId: Int,
    val CentralQuestion: String,
    val InputIdeation: Int,
    val PhaseId: Int
)