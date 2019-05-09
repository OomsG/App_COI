package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.surveys.Survey
import be.kdg.cityofideas.model.ideations.Ideation
import java.io.Serializable

data class Phase(
    val PhaseId: Int,
    val PhaseNr: Int?,
    val PhaseName: String?,
    val Description: String?,
    val StartDate: String?,
    val EndDate: String?,
    val Project: Project?,
    val Ideations: Collection<Ideation>?,
    val Surveys: Collection<Survey>?
) : Serializable