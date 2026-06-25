package gehoertrainer.controller.data

import gehoertrainer.model.SettingsModel

/**
 * zur Steuerung des Speichern/Laden von RateEinstellungen
 */
interface IPreferencesRepository {

    /**
     * speichern der Einstellungen
     */
    fun saveSettings(settings: SettingsModel)

    /**
     * laden der Einstellungen
     */
    fun loadSettings(): SettingsModel
}