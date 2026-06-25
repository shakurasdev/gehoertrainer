package gehoertrainer.service.audio

import org.junit.Assert.assertEquals
import org.junit.Test

class AudioDeviceTest {

    private val device = object : IAudioDevice {

        override fun play(notes: List<Int>) {}

        override fun cleanup() {}
    }

    @Test
    fun filterPlayableNotes_begrenztNachUnten() {

        val result =
            device.filterPlayableNotes(
                17,
                63,
                listOf(5, 10, 17, 20)
            )

        assertEquals(
            listOf(17, 17, 17, 20),
            result
        )
    }

    @Test
    fun filterPlayableNotes_begrenztNachOben() {

        val result =
            device.filterPlayableNotes(
                17,
                63,
                listOf(60, 63, 70, 80)
            )

        assertEquals(
            listOf(60, 63, 63, 63),
            result
        )
    }
}