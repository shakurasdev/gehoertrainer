package com.example.a26ss_gehoertrainer.ui.highscore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a26ss_gehoertrainer.data.PreferencesManager
import com.example.a26ss_gehoertrainer.databinding.ActivityHighscoreBinding
import com.example.a26ss_gehoertrainer.model.SpielergebnisModel
import com.example.a26ss_gehoertrainer.ui.main.MainActivity

class HighscoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHighscoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHighscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings =
            PreferencesManager(this).loadSettings()

        val data = listOf(
            SpielergebnisModel(
                settings.id,
                settings.rounds,
                settings.grundtonVariabel,
                settings.intervalMin,
                settings.intervalMax,
                settings.polyphony,
                "TODO",
                0
            )
        )

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerView.adapter =
            HighscoreAdapter(data)

        binding.btnFilter.setOnClickListener {
            Toast.makeText(
                this,
                "Noch nicht implementiert",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnExchange.setOnClickListener {
            Toast.makeText(
                this,
                "Noch nicht implementiert",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnMainMenu.setOnClickListener {
            navigateToMain()
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
    }
}