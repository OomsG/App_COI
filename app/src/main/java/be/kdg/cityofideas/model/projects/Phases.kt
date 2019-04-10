package be.kdg.cityofideas.model.projects

import java.util.*

data class Phases(
    private val PhaseId:Int,
    private val PhaseNr: Int,
    private val PhaseName: String,
    private val Description: String,
    private val StartDate: Date,
    private val EndDte:Date
)