package com.example.a26ss_gehoertrainer.ui.guess

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.a26ss_gehoertrainer.databinding.ActivityGuessBinding
import com.example.a26ss_gehoertrainer.ui.main.MainActivity

class GuessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent =
                Intent(this, MainActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        }, 5000)
    }

    override fun onBackPressed() {
        // deaktiviert
    }
}