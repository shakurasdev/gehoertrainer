package com.example.a26ss_gehoertrainer.logic

import com.example.a26ss_gehoertrainer.model.SettingsModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SpiellogikTest {

    private lateinit var settings: SettingsModel

    @Before
    fun setup() {

        settings = SettingsModel(
            id = "Tester",
            rounds = 3,
            grundtonVariabel = false,
            intervalMin = 1,
            intervalMax = 12,
            polyphony = 2
        )
    }

    @Test
    fun constructor_createsValidInterval() {

        val logic = Spiellogik(settings)

        val notes = logic.getCurrentMidiNotes(22)

        assertEquals(2, notes.size)
    }

    @Test
    fun midiNotesHaveCorrectSize() {

        val logic = Spiellogik(settings)

        assertEquals(
            settings.polyphony,
            logic.getCurrentMidiNotes(22).size
        )
    }

    @Test
    fun fixedBaseToneShifts() {

        val logic = Spiellogik(settings)
        val shift = 28
        val notes = logic.getCurrentMidiNotes(shift)

        assertEquals(shift, notes.first())
    }

    @Test
    fun variableBaseToneStaysInRange() {
        val shift = 28

        val variableSettings =
            settings.copy(
                grundtonVariabel = true
            )

        repeat(1000) {

            val logic =
                Spiellogik(variableSettings)

            val baseTone =
                logic.getCurrentMidiNotes(shift).first()

            assertTrue(baseTone in shift - 11..shift + 11)
        }
    }

    @Test
    fun wrongSizeThrowsException() {

        val logic = Spiellogik(settings)

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            logic.raten(emptyList())
        }
    }

    @Test
    fun valueBelowMinThrowsException() {

        val logic = Spiellogik(settings)

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            logic.raten(listOf(0))
        }
    }

    @Test
    fun valueAboveMaxThrowsException() {

        val logic = Spiellogik(settings)

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            logic.raten(listOf(13))
        }
    }

    @Test
    fun getEndergebnisCopiesSettings() {

        val logic = Spiellogik(settings)

        val result =
            logic.getEndergebnis("device123")

        assertEquals(
            settings.id,
            result.playerId
        )

        assertEquals(
            settings.rounds,
            result.rounds
        )

        assertEquals(
            settings.grundtonVariabel,
            result.baseTone
        )

        assertEquals(
            settings.intervalMin,
            result.intervalMin
        )

        assertEquals(
            settings.intervalMax,
            result.intervalMax
        )

        assertEquals(
            settings.polyphony,
            result.polyphon
        )

        assertEquals(
            "device123",
            result.deviceId
        )
    }
}