package be.kdg.cityofideas.model.IoT

import be.kdg.cityofideas.model.Surveys.Questions
import be.kdg.cityofideas.model.datatypes.Positions
import be.kdg.cityofideas.model.ideations.Ideas

data class IotSetup(
    private val Code:String,
    private val Position: Positions,
    private val Idea: Ideas,
    private val Question: Questions
)