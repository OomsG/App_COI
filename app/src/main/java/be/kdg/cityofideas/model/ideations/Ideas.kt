package be.kdg.cityofideas.model.ideations

import be.kdg.cityofideas.model.IoT.IotSetup
import be.kdg.cityofideas.model.datatypes.Positions
import be.kdg.cityofideas.model.ideations.IdeaObjects.IdeaObject

data class Ideas(
    var IdeaId: Int,
    var Position: Positions?,
    var IdeaObjects: Collection<IdeaObject>?,
    var Reported: Boolean,
    var Title: String,
    var Ideation: Ideations,
    var IoTSetup: Collection<IotSetup>?,
    var Votes: Collection<Votes>,
    var Reactions: Collection<Reactions>

)