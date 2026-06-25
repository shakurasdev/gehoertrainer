package gehoertrainer.ui.highscore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.schmidt.a26ss_gehoertrainer.databinding.ActivityHighscoreBinding
import gehoertrainer.controller.data.InstallationIdManager
import gehoertrainer.controller.data.SpielergebnisRepository
import gehoertrainer.model.HighscoreFilter
import gehoertrainer.model.HighscoreRow
import gehoertrainer.model.SpielergebnisModel
import gehoertrainer.ui.filter.FilterActivity
import gehoertrainer.ui.main.MainActivity
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

                updateFilterText()

                loadHighscores()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHighscoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        updateFilterText()
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

        val installationId =
            InstallationIdManager(this)
                .getInstallationId()

        var results =
            SpielergebnisRepository(this)
                .load()

        currentFilter?.let { filter ->

            results =
                results.filter {

                    it.rounds == filter.rounds &&
                            it.baseTone == filter.baseTone &&
                            it.intervalMin == filter.intervalMin &&
                            it.intervalMax == filter.intervalMax &&
                            it.polyphon == filter.polyphony && (
                                !filter.ownIdOnly ||
                                it.deviceId == installationId
                            )
                }
        }

        val formatter =
            SimpleDateFormat(
                "yy/MM/dd",
                Locale.getDefault()
            )

        val rows =
            results
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
            //TODO implementieren
            Toast.makeText(
                this,
                "Noch nicht implementiert",
                Toast.LENGTH_SHORT
            ).show()
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

    private fun updateFilterText() {

        currentFilter?.let {

            binding.tvActiveFilter.text =
                "G: ${
                    if(it.baseTone) "V" else "F"
                } | min: ${it.intervalMin}" +
                        " | max: ${it.intervalMax}" +
                        " | poly: ${it.polyphony}" +
                        " | #: ${it.rounds}"

        } ?: run {

            binding.tvActiveFilter.text =
                "kein Filter"
        }
    }
}