package be.kdg.cityofideas.model.Surveys

import be.kdg.cityofideas.model.projects.Phases

data class Survey(
    val SurveyId: Int,
    val Title: String,
    val Phase: Phases,
    val Questions: Collection<Questions>
)