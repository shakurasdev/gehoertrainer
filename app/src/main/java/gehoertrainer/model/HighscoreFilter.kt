package gehoertrainer.model

import java.io.Serializable

data class HighscoreFilter(

    val rounds: Int,

    val baseTone: Boolean,

    val intervalMin: Int,

    val intervalMax: Int,

    val polyphony: Int,

    val ownIdOnly: Boolean

) : Serializable