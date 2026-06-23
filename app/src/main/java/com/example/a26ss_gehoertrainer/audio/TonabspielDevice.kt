package com.example.a26ss_gehoertrainer.audio

/**
 * eine Schnittstelle, die das Abspielen von gewählten Tönen erlaubt.
 * Töne sind dabei repräsentiert durch Int werte.
 *
 * Eine Implementierung sollte immer angeben, welcher Wertebereich abgedeckt ist
 */
interface TonabspielDevice {
    /**
     * spielt eine Liste von Tönen nacheinander und dann gleichzeitig
     * sollten die Töne außerhalb des gültigen Wertebereichs liegen,
     * wird der niedrigste bzw höchste verfügbare Ton abgespielt
     */
    fun play(notes: List<Int>)

    /**
     * Hilfsfunktion für play, um Noten im Wertebereich zu halten
     */
    fun filterPlayableNotes(min:Int, max:Int, notes: List<Int>) : List<Int> {
        return notes.map {
            if(it < min) min
            else if(it > max) max
            else it
        }
    }

    /**
     * gibt die Ressourcen frei
     * für sauberes Beenden des Geräts
     */
    fun cleanup()

    }
