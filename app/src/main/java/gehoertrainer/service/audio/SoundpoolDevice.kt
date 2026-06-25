package gehoertrainer.service.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.os.Looper

/**
 * erlaubt das Abspielen von audio samples mit numerischen Werten 17 bis 63
 * (28 entspricht C3)
 */
class SoundpoolDevice (
    private val context: Context
) : IAudioDevice {

    private val soundPool: SoundPool

    private val handler = Handler(Looper.getMainLooper())

    private val sounds = mutableMapOf<Int, Int>()

    init {

        val audioAttributes =
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

        soundPool =
            SoundPool.Builder()
                .setMaxStreams(8)
                .setAudioAttributes(audioAttributes)
                .build()

        loadAllSamples()
    }

    private fun loadAllSamples() {

        for (note in 17..63) {

            val resourceId =
                context.resources.getIdentifier(
                    "note_$note",
                    "raw",
                    context.packageName
                )

            if (resourceId != 0) {

                sounds[note] =
                    soundPool.load(
                        context,
                        resourceId,
                        1
                    )
            }
        }
    }

    override fun play(notes: List<Int>) {
        //28 entspricht C3 als piano Grundton der App
        // (wäre 48 in Midi https://inspiredacoustics.com/en/MIDI_note_numbers_and_center_frequencies)
        val tonShift = 28
        val playableNotes = filterPlayableNotes(17, 63, notes.map { it + tonShift })

        handler.removeCallbacksAndMessages(null)

        val noteDuration = 1000L
        val pauseDuration = 250L

        playableNotes.forEachIndexed { index, note ->

            val delay =
                index * (noteDuration + pauseDuration)

            handler.postDelayed({

                playSingle(note)

            }, delay)
        }

        val chordDelay =
            playableNotes.size * (noteDuration + pauseDuration)

        handler.postDelayed({

            playableNotes.forEach { note ->
                playSingle(note)
            }

        }, chordDelay)
    }

    private fun playSingle(note: Int) {

        sounds[note]?.let { soundId ->

            soundPool.play(
                soundId,
                1f,
                1f,
                1,
                0,
                1f
            )
        }
    }

    override fun cleanup() {
        soundPool.release()
    }
}