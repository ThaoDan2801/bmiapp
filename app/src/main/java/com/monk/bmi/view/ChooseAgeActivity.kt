package com.monk.bmi.view

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.R
import com.monk.bmi.databinding.ActivityChooseAgeBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.ChooseAgeViewModel

class ChooseAgeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseAgeBinding
    private lateinit var viewModel: ChooseAgeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        clickEvent()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            BMIViewModelFactory(
                this.getSharedPreferences(
                    AppConstants.KEY_BMI_APP_PREFS,
                    MODE_PRIVATE
                )
            )
        )[ChooseAgeViewModel::class.java]
    }

    private fun setupView() {
        binding.toolbar.setIconLeft(R.drawable.icon_back)
        if (viewModel.getGender()) {
            binding.imgGender.setImageResource(R.drawable.img_male_gender_on)
        } else {
            binding.imgGender.setImageResource(R.drawable.img_female_gender_on)
        }
    }

    private fun clickEvent() {
        var ageNumber = 0
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvAge.text =
                    String.format("%d %s", progress, getString(R.string.ChooseAge_A_02))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let { ageNumber = it }
            }
        })

        binding.btnNext.btn.setOnClickListener {
            if (ageNumber == 0) {
                Toast.makeText(this, getString(R.string.ChooseAge_A_04), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveAge(ageNumber)
                nextScreen()
            }
        }

        binding.toolbar.getLeftIcon().setOnClickListener {
            finish()
        }
    }

    private fun nextScreen() {
        val intent = Intent(this, ChooseWeightAndHeightActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.getAge() != -1) {
            binding.tvAge.text = String.format(
                "%s %s",
                viewModel.getAge().toString(),
                getString(R.string.ChooseAge_A_02)
            )

        } else {
            binding.tvAge.text = getString(R.string.ChooseAge_A_03)
        }
    }
}