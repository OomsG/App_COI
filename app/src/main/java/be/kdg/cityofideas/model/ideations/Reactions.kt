package be.kdg.cityofideas.model.ideations

data class Reactions(
    val ReactionId: Int,
    val ReactionText: String,
    val Reported: Boolean,
    val Ideation: Ideations,
    val Idea: Ideas,
    val Like: Collection<Likes>
)