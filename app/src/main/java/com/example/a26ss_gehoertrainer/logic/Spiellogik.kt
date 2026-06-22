package com.example.a26ss_gehoertrainer.logic

import com.example.a26ss_gehoertrainer.model.SettingsModel
import com.example.a26ss_gehoertrainer.model.SpielergebnisModel
import kotlin.random.Random

/**
 * verwaltet einen Spielablauf
 */
class Spiellogik {

    private var correct = 0
    private var falseCount = 0

    private var currentInterval: List<Int>

    private val settings: SettingsModel

    constructor(settings: SettingsModel) {
        this.settings = settings
        currentInterval = newInterval()
    }

    /**
     * erzeugt die numerischen Werte des nächsten Intervalls zum Abspielen
     *
     */
    fun newInterval(): List<Int> {
        //A3 entspricht 57 als byte für MidiManager
        val grundton = 57 + (if(!settings.grundtonVariabel) 0 else Random.nextInt(-11, 12))
        val allPossibleValues = (settings.intervalMin + grundton..settings.intervalMax + grundton).toList()
        val randomUniqueValues = allPossibleValues.shuffled().take(settings.polyphony - 1)
        return randomUniqueValues.sorted()
    }

    fun abspielen() {

        // später implementieren
    }

    fun raten(intervals: List<Int>) {
        if(intervals.size != settings.polyphony - 1)
            throw IllegalArgumentException("intervall.size muss polyphony-1 sein")
        val min = settings.intervalMin
        val max = settings.intervalMax
        if (intervals.any { it !in min..max }) {
            throw IllegalArgumentException("Intervalle müssen zwischen $min und $max liegen")
        }

        // später implementieren

        correct++

        currentInterval = newInterval()
    }

    fun getC(): Int {
        return correct
    }

    fun getF(): Int {
        return falseCount
    }

    fun getEndergebnis(): SpielergebnisModel {
        return SpielergebnisModel(
            settings.id,
            settings.rounds,
            settings.grundtonVariabel,
            settings.intervalMin,
            settings.intervalMax,
            settings.polyphony,
            "TODO",
            correct
            )
    }
}