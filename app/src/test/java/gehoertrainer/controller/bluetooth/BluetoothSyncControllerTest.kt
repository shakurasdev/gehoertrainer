package gehoertrainer.controller.bluetooth

import gehoertrainer.controller.bluetooth.IBluetoothManager
import gehoertrainer.model.SpielergebnisModel
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever

class BluetoothSyncControllerTest {

    @Test
    fun sync_ruft_alle_bluetooth_methoden_auf() {

        val bluetooth =
            mock<IBluetoothManager>()

        val controller =
            BluetoothSyncController(bluetooth)

        controller.sync(emptyList())

        verify(bluetooth).connect()

        verify(bluetooth)
            .exchangeResults(emptyList())

        verify(bluetooth).disconnect()
    }

    @Test
    fun sync_ruft_methoden_in_richtiger_reihenfolge_auf() {

        val bluetooth =
            mock<IBluetoothManager>()

        val controller =
            BluetoothSyncController(bluetooth)

        controller.sync(emptyList())

        val order =
            inOrder(bluetooth)

        order.verify(bluetooth)
            .connect()

        order.verify(bluetooth)
            .exchangeResults(emptyList())

        order.verify(bluetooth)
            .disconnect()
    }

    @Test
    fun sync_uebergibt_ergebnisse() {

        val bluetooth =
            mock<IBluetoothManager>()

        val controller =
            BluetoothSyncController(bluetooth)

        val results =
            listOf(
                SpielergebnisModel(
                    "Tester",
                    5,
                    false,
                    1,
                    12,
                    2,
                    "device",
                    4,
                    123L
                )
            )

        controller.sync(results)

        verify(bluetooth)
            .exchangeResults(results)
    }

    @Test
    fun sync_wirft_fehler_wenn_connect_fehlgeschlagen() {

        val bluetooth =
            mock<IBluetoothManager>()

        whenever(bluetooth.connect())
            .thenThrow(
                RuntimeException("Bluetooth nicht verfügbar")
            )

        val controller =
            BluetoothSyncController(bluetooth)

        assertThrows(RuntimeException::class.java) {

            controller.sync(emptyList())
        }
    }
}