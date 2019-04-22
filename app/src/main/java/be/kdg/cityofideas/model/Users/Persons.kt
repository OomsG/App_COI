package be.kdg.cityofideas.model.Users

import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Reactions
import be.kdg.cityofideas.model.projects.AdminProjects
import java.util.*

data class Persons(
    val FirstName: String,
    val LastName: String,
    val Sex: Sex,
    val BirthDate: Date,
    override val Password: String,
    override val Verified: Boolean,
    override val Zipcode: String,
    override val RoleType: RoleType,
    override val Reaction: Collection<Reactions>,
    override val Idea: Ideas,
    override val AdminProject: AdminProjects
) : LoggedInUsers()