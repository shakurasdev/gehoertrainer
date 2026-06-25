package gehoertrainer.controller.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import gehoertrainer.model.SpielergebnisModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpielergebnisRepositoryTest {

    private lateinit var repository: SpielergebnisRepository

    @Before
    fun setup() {

        val context =
            ApplicationProvider.getApplicationContext<android.content.Context>()

        context.getSharedPreferences(
            "highscores",
            android.content.Context.MODE_PRIVATE
        ).edit().clear().commit()

        repository =
            SpielergebnisRepository(context)
    }

    private fun createEntry() =
        SpielergebnisModel(
            playerId = "ABC",
            rounds = 5,
            baseTone = true,
            intervalMin = 1,
            intervalMax = 12,
            polyphon = 2,
            deviceId = "device1",
            correct = 4,
            timestamp = 123456L
        )

    @Test
    fun load_ohneEintraege_liefertLeereListe() {

        val result =
            repository.load()

        assertTrue(result.isEmpty())
    }

    @Test
    fun save_speichertEintrag() {

        val entry = createEntry()

        repository.save(entry)

        val loaded =
            repository.load()

        assertEquals(
            1,
            loaded.size
        )

        assertEquals(
            entry,
            loaded.first()
        )
    }

    @Test
    fun save_mehrereEintraege_werdenAlleGeladen() {

        val e1 =
            createEntry()

        val e2 =
            createEntry().copy(
                playerId = "XYZ",
                deviceId = "device2"
            )

        repository.save(e1)
        repository.save(e2)

        val loaded =
            repository.load()

        assertEquals(
            2,
            loaded.size
        )
    }

    @Test
    fun save_behaeltReihenfolgeBei() {

        val e1 =
            createEntry()

        val e2 =
            createEntry().copy(
                playerId = "SECOND"
            )

        repository.save(e1)
        repository.save(e2)

        val loaded =
            repository.load()

        assertEquals(
            "ABC",
            loaded[0].playerId
        )

        assertEquals(
            "SECOND",
            loaded[1].playerId
        )
    }

    @Test
    fun save_undLoad_erhaltenAlleFelder() {

        val entry =
            SpielergebnisModel(
                playerId = "Player",
                rounds = 8,
                baseTone = false,
                intervalMin = 3,
                intervalMax = 20,
                polyphon = 4,
                deviceId = "dev123",
                correct = 7,
                timestamp = 999999L
            )

        repository.save(entry)

        val loaded =
            repository.load()
                .first()

        assertEquals(entry, loaded)
    }
}