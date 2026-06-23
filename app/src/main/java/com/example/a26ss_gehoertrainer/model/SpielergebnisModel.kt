package com.example.a26ss_gehoertrainer.model

data class SpielergebnisModel(
    val playerId: String,
    val rounds: Int,
    val baseTone: Boolean,
    val intervalMin: Int,
    val intervalMax: Int,
    val polyphon: Int,
    val deviceId: String,
    val correct: Int,
    val timestamp: Long
)
