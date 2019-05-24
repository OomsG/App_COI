package be.kdg.cityofideas.model.IoT

import be.kdg.cityofideas.model.surveys.Question
import be.kdg.cityofideas.model.datatypes.Position
import be.kdg.cityofideas.model.ideations.Idea
import java.io.Serializable

data class IoTSetup(
    val Code: String,
    val Position: Position?,
    val Idea: Idea?,
    val Question: Question?
) : Serializable