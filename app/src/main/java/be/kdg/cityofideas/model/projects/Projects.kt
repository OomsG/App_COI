package be.kdg.cityofideas.model.projects

import org.w3c.dom.Text
import java.util.*

data class Projects(
    private val ProjectId:Int,
    private val ProjectName:String,
    private val Logo: String,
    private val StartDate: Date,
    private val EndDate:Date,
    private val Objective:String,
    private val Description: String,
    private val LocationId:Int,
    private val Satus:String,
    private val PlatformId:Int
)