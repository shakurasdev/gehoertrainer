package gehoertrainer.controller.bluetooth

import gehoertrainer.model.SpielergebnisModel

/**
 * steuert den Ablauf von Bluetooth Verbindungen für Highscore Austausch
 */
class BluetoothSyncController(
    private val bluetooth: IBluetoothManager
) {


    /**
     * führt Verbindungsaufbau, Datenaustausch und Verbindungsabbruch aus
     */
    fun sync(results: List<SpielergebnisModel>) {
        try {
            bluetooth.connect()
        } catch (e: RuntimeException) {
            //TODO mit timout 1mal neu versuchen dann fehlermeldung
        }
        try {
            bluetooth.exchangeResults(results)
        } catch (e: RuntimeException) {
            //TODO meldung ausgeben dass Daten fehlerhaft sind
        }
        bluetooth.disconnect()
    }
}