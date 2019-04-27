package be.kdg.cityofideas.model.projects

import android.location.Location
import org.w3c.dom.Text
import java.time.LocalDate
import java.util.*

data class Projects(
    val ProjectId: Int,
    val ProjectName: String,
    val Logo: String?,
    val StartDate: String,
    val EndDate: String,
    val Objective: String,
    val Description: String,
    val Location: Location,
    val Status: String,
    val Platform : Platforms,
    val Phases: Collection<Phases>,
    val AdminProjects:Collection<AdminProjects>
)