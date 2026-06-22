package com.example.a26ss_gehoertrainer.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.a26ss_gehoertrainer.data.PreferencesManager
import com.example.a26ss_gehoertrainer.databinding.ActivitySettingsBinding
import com.example.a26ss_gehoertrainer.model.SettingsModel
import com.example.a26ss_gehoertrainer.ui.guess.GuessActivity
import com.example.a26ss_gehoertrainer.ui.main.MainActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //einstellungen laden
        val prefs = PreferencesManager(this)
        val settings = prefs.loadSettings()

        binding.etId.setText(settings.id)
        binding.sliderRounds.value = settings.rounds.toFloat()
        binding.tvRoundValue.text = settings.rounds.toString()
        binding.switchBaseTone.isChecked = settings.baseTone
        binding.sliderIntervalMin.value = settings.intervalMin.toFloat()
        binding.sliderIntervalMax.value = settings.intervalMax.toFloat()
        binding.sliderPolyphony.value = settings.polyphony.toFloat()
        binding.tvIntervalMinValue.text = settings.intervalMin.toString()
        binding.tvIntervalMaxValue.text = settings.intervalMax.toString()
        binding.tvPolyphonyValue.text = settings.polyphony.toString()

        //Rundenanzahl
        binding.sliderRounds.addOnChangeListener { _, value, _ ->
            binding.tvRoundValue.text = value.toInt().toString()
        }

        //Abbrechen
        binding.btnCancel.setOnClickListener {
            navigateToMain()
        }

        //Raten starten
        binding.btnStart.setOnClickListener {

            var id = binding.etId.text.toString().trim()

            if (id.isEmpty()) {
                id = "Keks"
            }

            val newSettings = SettingsModel(
                id = id,
                rounds = binding.sliderRounds.value.toInt(),
                baseTone = binding.switchBaseTone.isChecked,
                intervalMin = binding.sliderIntervalMin.value.toInt(),
                intervalMax = binding.sliderIntervalMax.value.toInt(),
                polyphony = binding.sliderPolyphony.value.toInt()
            )

            prefs.saveSettings(newSettings)

            startActivity(
                Intent(this, GuessActivity::class.java)
            )

            finish()
        }

        //android back button
        onBackPressedDispatcher.addCallback(this) {
            navigateToMain()
        }

        //grundton switch
        fun updateBaseToneText() {

            binding.tvBaseToneMode.text =
                if (binding.switchBaseTone.isChecked)
                    "variabel"
                else
                    "fest"
        }

        updateBaseToneText()

        binding.switchBaseTone.setOnCheckedChangeListener { _, _ ->
            updateBaseToneText()
        }

        //intervall logik
        binding.sliderIntervalMin.addOnChangeListener { _, value, _ ->

            val min = value.toInt()

            if (min >= binding.sliderIntervalMax.value.toInt()) {

                binding.sliderIntervalMax.value =
                    (min + 1).coerceAtMost(24).toFloat()
            }

            binding.tvIntervalMinValue.text = min.toString()

            binding.tvIntervalMaxValue.text =
                binding.sliderIntervalMax.value.toInt().toString()
        }
        binding.sliderIntervalMax.addOnChangeListener { _, value, _ ->

            val max = value.toInt()

            if (max <= binding.sliderIntervalMin.value.toInt()) {

                binding.sliderIntervalMin.value =
                    (max - 1).coerceAtLeast(1).toFloat()
            }

            binding.tvIntervalMaxValue.text = max.toString()

            binding.tvIntervalMinValue.text =
                binding.sliderIntervalMin.value.toInt().toString()
        }

        //polyphon slider
        binding.sliderPolyphony.addOnChangeListener { _, value, _ ->
            binding.tvPolyphonyValue.text =
                value.toInt().toString()
        }
    }

    private fun navigateToMain() {

        val intent = Intent(this, MainActivity::class.java)

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}