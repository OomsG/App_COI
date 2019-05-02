package be.kdg.cityofideas.model.Surveys

import be.kdg.cityofideas.model.IoT.IotSetup

data class Questions(
    val QuestionId: Int,
    val QuestionNr: Int,
    val QuestionText: String,
    val QuestionType: QuestionType,
    val Survey: Survey,
    val Answer: Collection<Answers>,
    val IoTSetups: Collection<IotSetup>?

)