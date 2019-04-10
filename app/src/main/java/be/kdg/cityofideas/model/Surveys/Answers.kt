package be.kdg.cityofideas.model.Surveys

data class Answers(
    private val AnswerId :Int,
    private val AnswerText: String,
    private val TotalTimesChosen: Int,
    private val Question: Questions
)