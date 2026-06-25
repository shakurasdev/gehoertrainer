package gehoertrainer.controller.logic

import gehoertrainer.model.SpielergebnisModel

/**
 * Verwaltet einen Spielablauf mit seinen Einstellungen und trackt die Rateergebnisse.
 * Liefert die Notenwerte für ein IAudioDevice
 */
interface ISpiellogik {


    /**
     * liefert die numerischen Werte des aktuellen intervalls einschließlich grundton an erster stelle.
     */
    fun getCurrentMidiNotes(): List<Int>

    /**
     * erzeugt die numerischen Werte des nächsten Intervalls zum Abspielen.
     * der wert 0 entspricht dem grundton, der für das Abspielen als Grundton der App vorgesehen ist.
     * also enthält die Liste jeweils die abstände von diesem grundton.
     *
     * Die Werte können auch negativ sein bis zu -11, wenn in SettingsModel grundtonVariabel == true.
     *
     * @return sortierte Liste, element an index 0 ist der grundton
     */
    fun newInterval(): List<Int>

    /**
     * bewertet die gegebenen intervalle.
     * wenn alle korrekt sind, wird this.correct erhöht, sonst this.falseCount erhöht.
     * abschließend werden die intervalle für die nächste Raterunde erzeugt.
     *
     * wenn correct + falseCount == settings.rounds passiert nichts.
     *
     * @throws IllegalArgumentException wenn intervals.size ungültig oder die geratenen werte nicht innerhalb intervalMin und intervalMax sind
     */
    fun raten(intervals: List<Int>)

    /**
     * liefert die Anzahl der richtig geratenen Runden
     */
    fun getCorrectCount(): Int

    /**
     * liefert die Anzahl der falsch geratenen Runden
     */
    fun getFalseCount(): Int

    /**
     * liefert den finalen Punktestand und Daten über Spieleinstellungen
     *
     * @param installationId die InstallationsID zur späteren Unterscheidung von Einträgen
     * @throws IllegalArgumentException wenn installationId leer
     */
    fun getEndergebnis(installationId: String): SpielergebnisModel


}