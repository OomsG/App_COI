package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.IoT.IotSetup
import be.kdg.cityofideas.model.users.User
import be.kdg.cityofideas.model.datatypes.Position
import java.io.Serializable

data class Idea(
    var IdeaId: Int,
    var Position: Position?,
    var IdeaObjects: Collection<IdeaObject>?,
    var Reported: Boolean?,
    var Title: String?,
    var Ideation: Ideation?,
    var User: User?,
    var IoTSetup: Collection<IotSetup>?,
    var Votes: Collection<Vote>?,
    var Reactions: Collection<Reaction>?
) : Serializable