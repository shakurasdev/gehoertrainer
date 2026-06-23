package com.example.a26ss_gehoertrainer.ui.guess

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.a26ss_gehoertrainer.audio.SoundpoolDevice
import com.example.a26ss_gehoertrainer.data.HighscoreManager
import com.example.a26ss_gehoertrainer.data.InstallationIdManager
import com.example.a26ss_gehoertrainer.data.PreferencesManager
import com.example.a26ss_gehoertrainer.databinding.ActivityGuessBinding
import com.example.a26ss_gehoertrainer.logic.Spiellogik
import com.google.android.material.slider.Slider

class GuessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuessBinding

    private lateinit var spiellogik: Spiellogik

    private lateinit var soundpoolDevice: SoundpoolDevice

    private val sliders = mutableListOf<Slider>()

    private var currentRound = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings =
            PreferencesManager(this).loadSettings()

        val installationId =
            InstallationIdManager(this).getInstallationId()

        soundpoolDevice = SoundpoolDevice(this)

        spiellogik = Spiellogik(settings)

        binding.tvRound.text =
            "Runde $currentRound/${settings.rounds}"

        createSliders(
            settings.polyphony,
            settings.intervalMin,
            settings.intervalMax
        )

        binding.imgLogo.setOnClickListener {
            //28 entspricht C3 als piano Grundton der App
            // (wäre 48 in Midi https://inspiredacoustics.com/en/MIDI_note_numbers_and_center_frequencies)
            val tonShift = 28

            val toene = spiellogik.getCurrentMidiNotes(tonShift)
            soundpoolDevice.play(toene)
        }

        binding.btnConfirm.setOnClickListener {

            val values =
                sliders.map { it.value.toInt() }

            try {
                spiellogik.raten(values)
                currentRound++
            } catch (e: IllegalArgumentException) {
                //TODO toast zeigen
                return@setOnClickListener
            }

            binding.tvCorrect.text =
                "Correct: ${spiellogik.getC()}"

            binding.tvFalse.text =
                "False: ${spiellogik.getF()}"


            if(currentRound > settings.rounds) {
                var ergebnis = spiellogik.getEndergebnis(installationId)
                HighscoreManager(this).save(ergebnis)
                finish()
            } else {
                binding.tvRound.text =
                    "Runde $currentRound/${settings.rounds}"
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun createSliders(
        count: Int,
        min: Int,
        max: Int
    ) {

        repeat(count - 1) { index ->

            val row = LinearLayout(this)

            row.orientation =
                LinearLayout.HORIZONTAL

            val label = TextView(this)

            label.text = "Intervall ${index + 1}"

            label.layoutParams =
                LinearLayout.LayoutParams(
                    250,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            val slider = Slider(this)

            slider.valueFrom = min.toFloat()
            slider.valueTo = max.toFloat()
            slider.stepSize = 1f
            slider.value = min.toFloat()

            slider.layoutParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )

            sliders.add(slider)

            row.addView(label)
            row.addView(slider)

            binding.sliderContainer.addView(row)
        }
    }

    override fun onDestroy() {

        soundpoolDevice.cleanup()

        super.onDestroy()
    }
}