package be.kdg.cityofideas.model.projects

import android.graphics.Bitmap
import android.location.Location
import org.w3c.dom.Text
import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class Projects(
    val ProjectId: Int,
    val ProjectName: String,
    val Logo: String,
    var LogoImage: Bitmap,
    val StartDate: String,
    val EndDate: String,
    val Objective: String,
    val Description: String,
    val BackgroundImage: String,
    var BackgroundImg: Bitmap,
    val Location: Location,
    val Status: String,
    val Platform: Platforms,
    val BackgroundImage : String,
    val Phases: Collection<Phases>,
    val AdminProjects: Collection<AdminProjects>

) : Serializable