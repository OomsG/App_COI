package be.kdg.cityofideas.model.projects

import be.kdg.cityofideas.model.Users.LoggedInUsers

data class AdminProjects(
     val AdminProjectId:Int,
     val Project: Projects,
     val Admin: LoggedInUsers
)