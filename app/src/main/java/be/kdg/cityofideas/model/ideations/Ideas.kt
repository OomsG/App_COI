package be.kdg.cityofideas.model.ideations

data class Ideas(
    var IdeaId: Int,
    var PositionPositinId: Int,
    var Video: String,
    var Image: String,
    var Theme: String,
    var Text: String,
    var Title: String,
    var LoggedInUserId: Int,
    var IdeationId: Int
)