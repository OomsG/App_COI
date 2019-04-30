package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.Surveys.Survey
import be.kdg.cityofideas.model.ideations.Ideations
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Phases(
    val PhaseId: Int,
    val PhaseNr: Int,
    val PhaseName: String,
    val Description: String,
    val StartDate: String,
    val EndDte: String,
    val Project: Projects,
    val Ideations: Collection<Ideations>,
    val Surveys: Collection<Survey>
)