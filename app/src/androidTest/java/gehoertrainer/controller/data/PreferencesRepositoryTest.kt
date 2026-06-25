package gehoertrainer.controller.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import gehoertrainer.model.SettingsModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PreferencesRepositoryTest {

    private lateinit var repository: PreferencesRepository

    @Before
    fun setup() {

        val context =
            ApplicationProvider.getApplicationContext<Context>()

        context.getSharedPreferences(
            "settings",
            Context.MODE_PRIVATE
        ).edit().clear().commit()

        repository = PreferencesRepository(context)
    }

    @Test
    fun loadSettings_returnsDefaultValues_whenNothingSaved() {

        val settings = repository.loadSettings()

        assertEquals("Keks", settings.id)
        assertEquals(1, settings.rounds)
        assertFalse(settings.grundtonVariabel)
        assertEquals(1, settings.intervalMin)
        assertEquals(12, settings.intervalMax)
        assertEquals(2, settings.polyphony)
    }

    @Test
    fun saveSettings_thenLoadSettings_returnsSameValues() {

        val original =
            SettingsModel(
                id = "Tester",
                rounds = 8,
                grundtonVariabel = true,
                intervalMin = 3,
                intervalMax = 17,
                polyphony = 4
            )

        repository.saveSettings(original)

        val loaded = repository.loadSettings()

        assertEquals(original.id, loaded.id)
        assertEquals(original.rounds, loaded.rounds)
        assertEquals(
            original.grundtonVariabel,
            loaded.grundtonVariabel
        )
        assertEquals(
            original.intervalMin,
            loaded.intervalMin
        )
        assertEquals(
            original.intervalMax,
            loaded.intervalMax
        )
        assertEquals(
            original.polyphony,
            loaded.polyphony
        )
    }

    @Test
    fun saveSettings_overwritesPreviousValues() {

        repository.saveSettings(
            SettingsModel(
                id = "Alt",
                rounds = 2,
                grundtonVariabel = false,
                intervalMin = 1,
                intervalMax = 5,
                polyphony = 2
            )
        )

        repository.saveSettings(
            SettingsModel(
                id = "Neu",
                rounds = 10,
                grundtonVariabel = true,
                intervalMin = 6,
                intervalMax = 24,
                polyphony = 4
            )
        )

        val loaded = repository.loadSettings()

        assertEquals("Neu", loaded.id)
        assertEquals(10, loaded.rounds)
        assertTrue(loaded.grundtonVariabel)
        assertEquals(6, loaded.intervalMin)
        assertEquals(24, loaded.intervalMax)
        assertEquals(4, loaded.polyphony)
    }
}