package com.example.a26ss_gehoertrainer.ui.filter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a26ss_gehoertrainer.databinding.ActivityFilterBinding
import com.example.a26ss_gehoertrainer.model.HighscoreFilter

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filter =
            intent.getSerializableExtra("filter")
                    as? HighscoreFilter

        filter?.let {

            binding.sliderRounds.value =
                it.rounds.toFloat()

            binding.switchBaseTone.isChecked =
                it.baseTone

            binding.sliderIntervalMin.value =
                it.intervalMin.toFloat()

            binding.sliderIntervalMax.value =
                it.intervalMax.toFloat()

            binding.sliderPolyphony.value =
                it.polyphony.toFloat()

            binding.switchOwnId.isChecked =
                it.ownIdOnly
        }

        setupListeners()
        updateLabels()
    }

    private fun setupListeners() {

        binding.sliderRounds.addOnChangeListener { _, value, _ ->
            binding.tvRoundValue.text =
                value.toInt().toString()
        }

        binding.sliderIntervalMin.addOnChangeListener { _, value, _ ->

            val min = value.toInt()

            if (min >= binding.sliderIntervalMax.value.toInt()) {

                binding.sliderIntervalMax.value =
                    (min + 1).coerceAtMost(24).toFloat()
            }

            binding.tvIntervalMinValue.text =
                min.toString()

            binding.tvIntervalMaxValue.text =
                binding.sliderIntervalMax.value.toInt().toString()
        }

        binding.sliderIntervalMax.addOnChangeListener { _, value, _ ->

            val max = value.toInt()

            if (max <= binding.sliderIntervalMin.value.toInt()) {

                binding.sliderIntervalMin.value =
                    (max - 1).coerceAtLeast(1).toFloat()
            }

            binding.tvIntervalMaxValue.text =
                max.toString()

            binding.tvIntervalMinValue.text =
                binding.sliderIntervalMin.value.toInt().toString()
        }

        binding.sliderPolyphony.addOnChangeListener { _, value, _ ->

            binding.tvPolyphonyValue.text =
                value.toInt().toString()
        }

        binding.switchBaseTone.setOnCheckedChangeListener { _, isChecked ->

            binding.tvBaseToneMode.text =
                if (isChecked) "variabel"
                else "fest"
        }

        binding.btnApply.setOnClickListener {

            val resultIntent = Intent()

            resultIntent.putExtra(
                "filter",
                HighscoreFilter(
                    rounds =
                        binding.sliderRounds.value.toInt(),

                    baseTone =
                        binding.switchBaseTone.isChecked,

                    intervalMin =
                        binding.sliderIntervalMin.value.toInt(),

                    intervalMax =
                        binding.sliderIntervalMax.value.toInt(),

                    polyphony =
                        binding.sliderPolyphony.value.toInt(),

                    ownIdOnly =
                        binding.switchOwnId.isChecked
                )
            )

            setResult(
                RESULT_OK,
                resultIntent
            )

            finish()
        }

        binding.btnCancel.setOnClickListener {

            setResult(RESULT_CANCELED)

            finish()
        }
    }

    private fun updateLabels() {

        binding.tvRoundValue.text =
            binding.sliderRounds.value.toInt().toString()

        binding.tvIntervalMinValue.text =
            binding.sliderIntervalMin.value.toInt().toString()

        binding.tvIntervalMaxValue.text =
            binding.sliderIntervalMax.value.toInt().toString()

        binding.tvPolyphonyValue.text =
            binding.sliderPolyphony.value.toInt().toString()

        binding.tvBaseToneMode.text =
            if (binding.switchBaseTone.isChecked)
                "variabel"
            else
                "fest"
    }
}