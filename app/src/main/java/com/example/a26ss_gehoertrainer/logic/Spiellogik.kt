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

    private val tonShift = 57 //entspricht A3 als midi Grundton der App

    private var currentInterval: List<Int>

    /**
     * der Abstand vom grundton der App zum Grundton der raterunde
     */
    private var currentVariance = 0

    private val settings: SettingsModel

    constructor(settings: SettingsModel) {
        this.settings = settings
        currentInterval = newInterval()
    }

    /**
     * erzeugt die numerischen Werte des nächsten Intervalls zum Abspielen
     * der wert 0 entspricht dem grundton, der für das Abspielen vorgesehen ist
     * also enthält die Liste jeweils die abstände von diesem grundton
     *
     * @return sortierte Liste, elem(0) ist der grundton
     */
    fun newInterval(): List<Int> {
        currentVariance =(if(!settings.grundtonVariabel) 0 else Random.nextInt(-11, 12))
        val allPossibleValues = (settings.intervalMin + currentVariance..settings.intervalMax + currentVariance).toList()
        val randomUniqueValues = allPossibleValues.shuffled().take(settings.polyphony - 1)
        return listOf(currentVariance) + randomUniqueValues.sorted()
    }

    /**
     * liefert die numerischen Werte des aktuellen intervalls einschließlich grundton an erster stelle,
     * erhöht um this.tonShift für Midi Abspielen
     */
    fun getCurrentMidiNotes(): List<Int> {
        return currentInterval.map { it + tonShift }
    }

    /**
     * bewertet die gegebenen intervalle
     * wenn alle korrekt sind, wird this.correct erhöht, sonst this.falseCount erhöht
     * abschließend werden die nächsten intervalle erzeugt
     *
     * wenn correct + falseCount == settings.rounds passiert nichts
     *
     * @throws IllegalArgumentException wenn intervals.size ungültig oder die geratenen werte nicht innerhalb intervalMin und intervalMax sind
     */
    fun raten(intervals: List<Int>) {
        if(intervals.size != settings.polyphony - 1)
            throw IllegalArgumentException("intervall.size muss polyphony-1 sein")
        val min = settings.intervalMin
        val max = settings.intervalMax
        if (intervals.any { it !in min..max }) {
            throw IllegalArgumentException("Intervalle müssen zwischen $min und $max liegen")
        }
        if(correct + falseCount == settings.rounds) {
            return
        }

        val ohneGrundton = currentInterval.drop(1)

        if(intervals.map { it + currentVariance }.sorted() == ohneGrundton.sorted()) {
            correct++
        } else {
            falseCount++
        }

        currentInterval = newInterval()
    }

    fun getC(): Int {
        return correct
    }

    fun getF(): Int {
        return falseCount
    }

    fun getEndergebnis(installationId: String): SpielergebnisModel {
        return SpielergebnisModel(
            settings.id,
            settings.rounds,
            settings.grundtonVariabel,
            settings.intervalMin,
            settings.intervalMax,
            settings.polyphony,
            installationId,
            correct
            )
    }
}