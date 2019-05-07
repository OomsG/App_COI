package be.kdg.cityofideas.model.surveys

import java.io.Serializable

data class Answer(
    val AnswerId: Int,
    val AnswerText: String?,
    val TotalTimesChosen: Int?,
    val Question: Question?
) : Serializable