package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.Users.LoggedInUsers

data class AdminProjects(
    private val AdminProjectId:Int,
    private val Project: Projects,
    private val Admin: LoggedInUsers
)