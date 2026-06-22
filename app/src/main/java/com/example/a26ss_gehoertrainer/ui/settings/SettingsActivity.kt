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

        val prefs = PreferencesManager(this)
        val settings = prefs.loadSettings()

        binding.etId.setText(settings.id)
        binding.sliderRounds.value = settings.rounds.toFloat()
        binding.tvRoundValue.text = settings.rounds.toString()
        binding.switchBaseTone.isChecked = settings.baseTone

        binding.sliderRounds.addOnChangeListener { _, value, _ ->
            binding.tvRoundValue.text = value.toInt().toString()
        }

        binding.btnCancel.setOnClickListener {
            navigateToMain()
        }

        binding.btnStart.setOnClickListener {

            var id = binding.etId.text.toString().trim()

            if (id.isEmpty()) {
                id = "Keks"
            }

            val newSettings = SettingsModel(
                id = id,
                rounds = binding.sliderRounds.value.toInt(),
                baseTone = binding.switchBaseTone.isChecked
            )

            prefs.saveSettings(newSettings)

            startActivity(
                Intent(this, GuessActivity::class.java)
            )

            finish()
        }

        onBackPressedDispatcher.addCallback(this) {
            navigateToMain()
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