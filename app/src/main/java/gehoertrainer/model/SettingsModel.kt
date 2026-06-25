package gehoertrainer.model

/**
 * kapselt die Einstellungen für Raten
 */
data class SettingsModel(
    val id: String,
    val rounds: Int,
    val grundtonVariabel: Boolean,
    val intervalMin: Int,
    val intervalMax: Int,
    val polyphony: Int
)
