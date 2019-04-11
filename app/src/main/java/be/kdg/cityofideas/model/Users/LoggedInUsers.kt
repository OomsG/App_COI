package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Reactions
import be.kdg.cityofideas.model.projects.AdminProjects

abstract class LoggedInUsers {
    abstract val Password: String
    abstract val Verified: Boolean
    abstract val Zipcode: String
    abstract val RoleType: RoleType
    abstract val Reaction: Collection<Reactions>
    abstract val Idea: Ideas
    abstract val AdminProject: AdminProjects
}