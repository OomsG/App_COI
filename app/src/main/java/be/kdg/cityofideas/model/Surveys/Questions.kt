package be.kdg.cityofideas.model.Surveys

import be.kdg.cityofideas.model.IoT.IotSetup

data class Questions(
    private val QuestionId:Int,
    private val QuestionNr:Int,
    private val QuestionText:String,
    private val QuestionType: QuestionType,
    private val Survey: Survey,
    private val Answer: Collection<Answers>,
    private val IoTSetups: IotSetup

)