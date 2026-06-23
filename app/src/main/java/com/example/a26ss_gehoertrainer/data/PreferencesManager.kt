package com.example.a26ss_gehoertrainer.data

import android.content.Context
import com.example.a26ss_gehoertrainer.model.SettingsModel
import androidx.core.content.edit

class PreferencesManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveSettings(settings: SettingsModel) {

        prefs.edit {
            putString(KEY_ID, settings.id)
                .putInt(KEY_ROUNDS, settings.rounds)
                .putBoolean(KEY_BASE_TONE, settings.grundtonVariabel)
                .putInt(KEY_INTERVAL_MIN, settings.intervalMin)
                .putInt(KEY_INTERVAL_MAX, settings.intervalMax)
                .putInt(KEY_POLYPHONY, settings.polyphony)
        }
    }

    fun loadSettings(): SettingsModel {

        return SettingsModel(
            id = prefs.getString(KEY_ID, "Keks") ?: "Keks",
            rounds = prefs.getInt(KEY_ROUNDS, 1),
            grundtonVariabel = prefs.getBoolean(KEY_BASE_TONE, false),

            intervalMin = prefs.getInt(KEY_INTERVAL_MIN, 1),
            intervalMax = prefs.getInt(KEY_INTERVAL_MAX, 12),

            polyphony = prefs.getInt(KEY_POLYPHONY, 2)
        )
    }

    companion object {

        private const val KEY_ID = "id"
        private const val KEY_ROUNDS = "rounds"
        private const val KEY_BASE_TONE = "base_tone"

        private const val KEY_INTERVAL_MIN = "interval_min"
        private const val KEY_INTERVAL_MAX = "interval_max"

        private const val KEY_POLYPHONY = "polyphony"
    }
}