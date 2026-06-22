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
                .putBoolean(KEY_BASE_TONE, settings.baseTone)
        }
    }

    fun loadSettings(): SettingsModel {

        return SettingsModel(
            prefs.getString(KEY_ID, "Keks") ?: "Keks",
            prefs.getInt(KEY_ROUNDS, 5),
            prefs.getBoolean(KEY_BASE_TONE, true)
        )
    }

    companion object {

        private const val KEY_ID = "id"
        private const val KEY_ROUNDS = "rounds"
        private const val KEY_BASE_TONE = "base_tone"
    }
}