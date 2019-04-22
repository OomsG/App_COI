package be.kdg.cityofideas.model.IoT

import be.kdg.cityofideas.model.Surveys.Questions
import be.kdg.cityofideas.model.datatypes.Positions
import be.kdg.cityofideas.model.ideations.Ideas

data class IotSetup(
     val Code:String,
     val Position: Positions,
     val Idea: Ideas,
     val Question: Questions
)