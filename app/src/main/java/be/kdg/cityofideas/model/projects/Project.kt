package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.datatypes.Location
import java.io.Serializable

data class Project(
    val ProjectId: Int,
    val ProjectName: String?,
    val Logo: String?,
    val StartDate: String?,
    val EndDate: String?,
    val Objective: String?,
    val Description: String?,
    val Location: Location?,
    val Status: String?,
    val Platform: Platform?,
    val BackgroundImage : String?,
    val Phases: Collection<Phase>?,
    val AdminProjects: Collection<AdminProject>?
) : Serializable