package be.kdg.cityofideas.model.ideations

import android.os.Parcel
import android.os.Parcelable
import be.kdg.cityofideas.model.IoT.IotSetup
import be.kdg.cityofideas.model.Users.Users
import be.kdg.cityofideas.model.datatypes.Positions
import be.kdg.cityofideas.model.ideations.IdeaObjects.IdeaObject
import java.io.Serializable

data class Ideas(
    var IdeaId: Int,
    var Position: Positions,
    var IdeaObjects : Collection<IdeaObject>,
    var Reported: Boolean,
    var Title: String,
    var Ideation : Ideations,
    var User: Users,
    var IoTSetup: Collection<IotSetup>,
    var Votes: ArrayList<Votes>,
    var Reactions : Collection<Reactions>
):Serializable