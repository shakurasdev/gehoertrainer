package gehoertrainer.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.htw.gehoertrainer.databinding.ActivityMainBinding
import gehoertrainer.ui.highscore.HighscoreActivity
import gehoertrainer.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }

        binding.btnBestenliste.setOnClickListener {
            startActivity(
                Intent(this, HighscoreActivity::class.java)
            )
        }
    }
}