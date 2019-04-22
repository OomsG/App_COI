package be.kdg.cityofideas.model.projects

import java.util.*

data class Phases(
     val PhaseId:Int,
     val PhaseNr: Int,
     val PhaseName: String,
     val Description: String,
     val StartDate: Date,
     val EndDte:Date
)