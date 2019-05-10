package be.kdg.cityofideas.model.surveys

import be.kdg.cityofideas.model.projects.Phase
import java.io.Serializable

data class Survey(
    val SurveyId: Int,
    val Title: String?,
    val Phase: Phase?,
    val Questions: Collection<Question>?
) : Serializable