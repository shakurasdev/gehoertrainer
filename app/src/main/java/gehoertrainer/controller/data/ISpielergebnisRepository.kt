package gehoertrainer.controller.data

import gehoertrainer.model.SpielergebnisModel

/**
 * zum Speichern/Laden von Spielergebnissen
 */
interface ISpielergebnisRepository {

    /**
     * lädt Ergebnisse
     */
    fun load(): List<SpielergebnisModel>

    /**
     * speichert Ergebnisse
     */
    fun save(result: SpielergebnisModel)
}