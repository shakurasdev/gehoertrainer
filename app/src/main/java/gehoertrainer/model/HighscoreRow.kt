package gehoertrainer.model

data class HighscoreRow(
    val place: Int,
    val percent: Int,
    val date: String,
    val playerId: String,
    val installationId: String
)