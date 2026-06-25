package gehoertrainer.controller.bluetooth

import gehoertrainer.model.SpielergebnisModel

/**
 * steuert Verbindungsablauf via Bluetooth
 */
interface IBluetoothManager {

    /**
     * verbindung aufbauen
     */
    fun connect()

    /**
     * ergebnisse austauschen
     */
    fun exchangeResults(results: List<SpielergebnisModel>)

    /**
     * verbindung beenden
     */
    fun disconnect()
}