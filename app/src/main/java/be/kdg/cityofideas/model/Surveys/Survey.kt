package be.kdg.cityofideas.model.Surveys

import be.kdg.cityofideas.model.projects.Phases

data class Survey(
    private val SurveyId:Int,
    private val Title: String,
    private val Phase:Phases,
    private val Questions: Collection<Questions>
)