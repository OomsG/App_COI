package be.kdg.cityofideas.model.projects

import java.io.Serializable

data class AdminProject(
    val AdminProjectId: Int,
    val Project: Project
) : Serializable