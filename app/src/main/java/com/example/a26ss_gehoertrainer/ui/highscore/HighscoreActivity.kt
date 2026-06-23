package com.example.a26ss_gehoertrainer.ui.highscore

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a26ss_gehoertrainer.data.HighscoreManager
import com.example.a26ss_gehoertrainer.databinding.ActivityHighscoreBinding
import com.example.a26ss_gehoertrainer.model.HighscoreFilter
import com.example.a26ss_gehoertrainer.model.HighscoreRow
import com.example.a26ss_gehoertrainer.model.SpielergebnisModel
import com.example.a26ss_gehoertrainer.ui.filter.FilterActivity
import com.example.a26ss_gehoertrainer.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HighscoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHighscoreBinding

    private var currentFilter: HighscoreFilter? = null
    private val filterLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if(result.resultCode == RESULT_OK) {

                currentFilter =
                    result.data?.getSerializableExtra(
                        "filter"
                    ) as? HighscoreFilter

                loadHighscores()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHighscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        binding.tvActiveFilter.text = "kein Filter"
        setupButtons()
    }

    override fun onResume() {
        super.onResume()
        loadHighscores()
    }

    private fun setupRecyclerView() {

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.recyclerView.adapter =
            HighscoreAdapter(emptyList())
    }

    private fun loadHighscores() {

        val formatter =
            SimpleDateFormat(
                "yy/MM/dd",
                Locale.getDefault()
            )

        val rows =
            HighscoreManager(this)
                .load()
                .sortedWith(
                    compareByDescending<SpielergebnisModel> {
                        it.correct.toDouble() / it.rounds
                    }
                        .thenByDescending {
                            it.rounds
                        }
                        .thenByDescending {
                            it.timestamp
                        }
                )
                .mapIndexed { index, result ->

                    val percent =
                        ((result.correct.toDouble()
                                / result.rounds) * 100)
                            .toInt()

                    HighscoreRow(
                        place = index + 1,
                        percent = percent,
                        date = formatter.format(
                            Date(result.timestamp)
                        ),
                        playerId = result.playerId,
                        installationId =
                            "#${result.deviceId.take(6)}"
                    )
                }

        binding.recyclerView.adapter =
            HighscoreAdapter(rows)
    }

    private fun setupButtons() {

        binding.btnFilter.setOnClickListener {

            val intent =
                Intent(
                    this,
                    FilterActivity::class.java
                )

            currentFilter?.let {

                intent.putExtra(
                    "filter",
                    it
                )
            }

            filterLauncher.launch(intent)
        }

        binding.btnExchange.setOnClickListener {

            // TODO
        }

        binding.btnMainMenu.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MainActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}