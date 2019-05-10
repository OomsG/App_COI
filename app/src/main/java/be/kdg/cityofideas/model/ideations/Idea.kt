package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.IoT.IotSetup
import be.kdg.cityofideas.model.users.User
import be.kdg.cityofideas.model.datatypes.Position
import java.io.Serializable

data class Idea(
    val IdeaId: Int,
    val Position: Position?,
    val IdeaObjects: Collection<IdeaObject>?,
    val IdeaTags: Collection<IdeaTag>?,
    val Reported: Boolean?,
    val Title: String?,
    val Ideation: Ideation?,
    val User: User?,
    val IoTSetup: Collection<IotSetup>?,
    val Votes: Collection<Vote>?,
    val Reactions: Collection<Reaction>?
) : Serializable