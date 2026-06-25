package gehoertrainer.controller.logic

import gehoertrainer.model.SettingsModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SpiellogikTest {

    private lateinit var settings: SettingsModel
    private lateinit var spiellogik: ISpiellogik

    @Before
    fun setup() {

        settings = SettingsModel(
            id = "Tester",
            rounds = 5,
            grundtonVariabel = false,
            intervalMin = 1,
            intervalMax = 12,
            polyphony = 2
        )

        spiellogik = Spiellogik(settings)
    }

    @Test
    fun getCurrentMidiNotes_liefertGenauPolyphonyWerte() {

        val notes = spiellogik.getCurrentMidiNotes()

        assertEquals(
            settings.polyphony,
            notes.size
        )
    }

    @Test
    fun newInterval_liefertGenauPolyphonyWerte() {

        val interval = spiellogik.newInterval()

        assertEquals(
            settings.polyphony,
            interval.size
        )
    }

    @Test
    fun correctUndFalseSindInitialNull() {

        assertEquals(
            0,
            spiellogik.getCorrectCount()
        )

        assertEquals(
            0,
            spiellogik.getFalseCount()
        )
    }

    @Test
    fun raten_mitFalscherAnzahlWirftException() {

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            spiellogik.raten(
                listOf(1, 2)
            )
        }
    }

    @Test
    fun raten_mitZuKleinemIntervallWirftException() {

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            spiellogik.raten(
                listOf(0)
            )
        }
    }

    @Test
    fun raten_mitZuGrossemIntervallWirftException() {

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            spiellogik.raten(
                listOf(13)
            )
        }
    }

    @Test
    fun falscherRateversuch_erhoehtFalseCount() {

        spiellogik.raten(
            listOf(settings.intervalMax)
        )

        assertEquals(
            1,
            spiellogik.getFalseCount()
        )

        assertEquals(
            0,
            spiellogik.getCorrectCount()
        )
    }

    @Test
    fun nachMaximalenRundenWerdenKeineWeiterenPunkteGezaehlt() {

        val kurzeSettings = SettingsModel(
            id = "Tester",
            rounds = 1,
            grundtonVariabel = false,
            intervalMin = 1,
            intervalMax = 12,
            polyphony = 2
        )

        val logik = Spiellogik(kurzeSettings)

        logik.raten(listOf(12))

        val falseVorher =
            logik.getFalseCount()

        logik.raten(listOf(12))

        assertEquals(
            falseVorher,
            logik.getFalseCount()
        )
    }

    @Test
    fun getEndergebnis_uebernimmtSettingsKorrekt() {

        val ergebnis =
            spiellogik.getEndergebnis(
                "device123"
            )

        assertEquals(
            settings.id,
            ergebnis.playerId
        )

        assertEquals(
            settings.rounds,
            ergebnis.rounds
        )

        assertEquals(
            settings.intervalMin,
            ergebnis.intervalMin
        )

        assertEquals(
            settings.intervalMax,
            ergebnis.intervalMax
        )

        assertEquals(
            settings.polyphony,
            ergebnis.polyphon
        )

        assertEquals(
            "device123",
            ergebnis.deviceId
        )
    }

    @Test
    fun getEndergebnis_mitLeeremInstallationIdWirftException() {

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            spiellogik.getEndergebnis("")
        }
    }
}