package be.kdg.cityofideas.model.ideations

data class IdeaTag(
    val IdeaTagId: Int,
    val Idea: Idea?,
    val Tag: Tag?
)