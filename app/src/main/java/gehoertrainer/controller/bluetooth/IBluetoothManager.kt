package gehoertrainer.controller.bluetooth

import gehoertrainer.model.SpielergebnisModel

/**
 * steuert Verbindungsablauf via Bluetooth
 */
interface IBluetoothManager {

    /**
     * verbindung aufbauen
     * @throws RuntimeException wenn Verbindungsprobleme bestehen
     */
    fun connect()

    /**
     * ergebnisse austauschen
     * @throws RuntimeException wenn erhaltene Daten fehlerhaft sind
     */
    fun exchangeResults(results: List<SpielergebnisModel>)

    /**
     * verbindung beenden
     */
    fun disconnect()
}