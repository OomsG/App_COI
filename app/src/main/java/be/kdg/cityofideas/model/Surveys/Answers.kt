package be.kdg.cityofideas.model.Surveys

data class Answers(
    val AnswerId: Int,
    val AnswerText: String,
    val TotalTimesChosen: Int,
    val Question: Questions
)