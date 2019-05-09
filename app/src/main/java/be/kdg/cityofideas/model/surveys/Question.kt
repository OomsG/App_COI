package be.kdg.cityofideas.model.surveys

import be.kdg.cityofideas.model.IoT.IotSetup
import java.io.Serializable

data class Question(
    val QuestionId: Int,
    val QuestionNr: Int?,
    val QuestionText: String?,
    val QuestionType: QuestionType?,
    val Survey: Survey?,
    val Answers: Collection<Answer>?,
    val IoTSetups: Collection<IotSetup>?
) : Serializable