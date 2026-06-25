package gehoertrainer.model

/**
 * kapselt die Einstellungen für Raten
 */
data class SettingsModel(
    val id: String,
    val rounds: Int,
    /**
     * gibt an, ob der grundton eines intervalls vom grundton der app abweichen darf.
     * die abweichung darf dabei -11 bis +11 sein
     */
    val grundtonVariabel: Boolean,
    val intervalMin: Int,
    val intervalMax: Int,
    val polyphony: Int
)
