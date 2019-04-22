package be.kdg.cityofideas.model.projects

import org.w3c.dom.Text
import java.util.*

data class Projects(
    val projectId: Int,
    val projectName: String,
    val logo: String?,
    val startDate: String,
    val endDate: String,
    val objective: String,
    val description: String,
    val locationId: Int?,
    val status: String,
    val platformId: Int?,
    val phases: Phases?,
    val adminProjects: AdminProjects?
)