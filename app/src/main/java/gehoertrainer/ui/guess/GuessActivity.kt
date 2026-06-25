package gehoertrainer.ui.guess

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import de.htw.gehoertrainer.databinding.ActivityGuessBinding
import com.google.android.material.slider.Slider
import gehoertrainer.controller.data.InstallationIdManager
import gehoertrainer.controller.data.PreferencesRepository
import gehoertrainer.controller.data.SpielergebnisRepository
import gehoertrainer.controller.logic.ISpiellogik
import gehoertrainer.controller.logic.Spiellogik
import gehoertrainer.controller.audio.IAudioDevice
import gehoertrainer.controller.audio.SoundpoolDevice

class GuessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuessBinding

    private lateinit var spiellogik: ISpiellogik

    private lateinit var audioDevice: IAudioDevice

    private val sliders = mutableListOf<Slider>()

    private var currentRound = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings =
            PreferencesRepository(this).loadSettings()

        val installationId =
            InstallationIdManager(this).getInstallationId()

        audioDevice = SoundpoolDevice(this)

        spiellogik = Spiellogik(settings)

        binding.tvRound.text =
            "Runde $currentRound/${settings.rounds}"

        createSliders(
            settings.polyphony,
            settings.intervalMin,
            settings.intervalMax
        )

        binding.imgLogo.setOnClickListener {

            val toene = spiellogik.getCurrentMidiNotes()
            audioDevice.play(toene)
        }

        binding.btnConfirm.setOnClickListener {

            val values =
                sliders.map { it.value.toInt() }

            try {
                spiellogik.raten(values)
                currentRound++
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    this,
                    "Sie haben irgendwie geschafft, unmögliche Werte zu schicken",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            binding.tvCorrect.text =
                "Correct: ${spiellogik.getCorrectCount()}"

            binding.tvFalse.text =
                "False: ${spiellogik.getFalseCount()}"


            if(currentRound > settings.rounds) {
                var ergebnis = spiellogik.getEndergebnis(installationId)
                SpielergebnisRepository(this).save(ergebnis)
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

        audioDevice.cleanup()

        super.onDestroy()
    }
}