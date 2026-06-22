package com.example.a26ss_gehoertrainer.ui.guess

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.a26ss_gehoertrainer.data.InstallationIdManager
import com.example.a26ss_gehoertrainer.data.PreferencesManager
import com.example.a26ss_gehoertrainer.databinding.ActivityGuessBinding
import com.example.a26ss_gehoertrainer.logic.Spiellogik
import com.google.android.material.slider.Slider

class GuessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuessBinding

    private lateinit var spiellogik: Spiellogik

    //private lateinit var midiPlayer: MidiPlayer

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

        //midiPlayer = MidiPlayer(this)

        spiellogik = Spiellogik(settings)

        binding.tvRound.text =
            "Runde $currentRound/${settings.rounds}"

        createSliders(
            settings.polyphony,
            settings.intervalMin,
            settings.intervalMax
        )

        binding.imgLogo.setOnClickListener {

            var toene = spiellogik.getCurrentMidiNotes()
            //TODO midiplayer wieder reinkommentieren
            //midiPlayer.play(toene)
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
                //TODO ergebnis in die sammlung von ergebnissen speichern
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
}