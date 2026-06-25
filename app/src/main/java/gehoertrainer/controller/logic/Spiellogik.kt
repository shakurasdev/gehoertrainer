package gehoertrainer.controller.logic

import gehoertrainer.model.SettingsModel
import gehoertrainer.model.SpielergebnisModel
import kotlin.random.Random

/**
 * verwaltet einen Spielablauf
 */
class Spiellogik : ISpiellogik {

    private var correct = 0
    private var falseCount = 0

    private var currentInterval: List<Int>

    /**
     * der Abstand vom grundton der App zum Grundton der raterunde.
     * deckt das gewünschte verhalten von settings.grundtonVariabel ab
     */
    private var currentVariance = 0

    private val settings: SettingsModel

    /**
     * @constructor primärer konstruktor
     * @param settings die Spieleinstellungen
     */
    constructor(settings: SettingsModel) {
        this.settings = settings
        currentInterval = newInterval()
    }

    override fun newInterval(): List<Int> {
        currentVariance =(if(!settings.grundtonVariabel) 0 else Random.nextInt(-11, 12))
        val allPossibleValues = (settings.intervalMin + currentVariance..settings.intervalMax + currentVariance).toList()
        val randomUniqueValues = allPossibleValues.shuffled().take(settings.polyphony - 1)
        return listOf(currentVariance) + randomUniqueValues.sorted()
    }


    override fun getCurrentMidiNotes(): List<Int> {
        return currentInterval
    }

    override fun raten(intervals: List<Int>) {
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

    override fun getCorrectCount(): Int {
        return correct
    }

    override fun getFalseCount(): Int {
        return falseCount
    }

    override fun getEndergebnis(installationId: String): SpielergebnisModel {
        if(installationId.isBlank()) throw IllegalArgumentException("id muss einen wert haben")

        return SpielergebnisModel(
            settings.id,
            settings.rounds,
            settings.grundtonVariabel,
            settings.intervalMin,
            settings.intervalMax,
            settings.polyphony,
            installationId,
            correct,
            System.currentTimeMillis()
        )
    }
}