package com.example.a26ss_gehoertrainer.model

/**
 * kapselt die Einstellungen für Raten
 */
data class SettingsModel(
    val id: String,
    val rounds: Int,
    val baseTone: Boolean,
    val intervalMin: Int,
    val intervalMax: Int,
    val polyphony: Int
)
