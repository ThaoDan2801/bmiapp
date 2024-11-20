package com.monk.bmi.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.R
import com.monk.bmi.databinding.ActivityChooseWeightAndHeightBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.ChooseWeightAndHeightViewModel
import kotlin.math.roundToInt

class ChooseWeightAndHeightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseWeightAndHeightBinding
    private lateinit var viewModel: ChooseWeightAndHeightViewModel

    var weightNumber = 0f
    var heightNumber = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseWeightAndHeightBinding.inflate(layoutInflater)
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
        )[ChooseWeightAndHeightViewModel::class.java]
    }

    private fun setupView() {
        binding.seekBarWeight.max = 100
        binding.seekBarHeight.max = 200
        if (viewModel.getGender()) {
            binding.imgGender.setImageResource(R.drawable.img_male_gender_on)
        } else {
            binding.imgGender.setImageResource(R.drawable.img_female_gender_on)
        }
        binding.toolbar.setIconLeft(R.drawable.icon_back)
    }

    private fun clickEvent() {
        binding.tvWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                weightNumber = if (s.toString() == "") {
                    0f
                } else {
                    s.toString().toFloat()
                }
                binding.seekBarWeight.progress = weightNumber.roundToInt()
            }
        })

        binding.tvHeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                heightNumber = if (s.toString() == "") {
                    0f
                } else {
                    s.toString().toFloat()
                }
                binding.seekBarHeight.progress = heightNumber.roundToInt()
            }
        })

        binding.seekBarWeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let { weightNumber = it.toFloat() }
                binding.tvWeight.setText(weightNumber.toString())
            }
        })

        binding.seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let { heightNumber = it.toFloat() }
                binding.tvHeight.setText(heightNumber.toString())
            }
        })

        binding.btnNext.btn.setOnClickListener {
            if (weightNumber == 0f || heightNumber == 0f) {
                Toast.makeText(
                    this,
                    getString(R.string.ChooseWeightAndHeight_A_07),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                saveWeightAndHeight()
                nextScreen()
            }
        }

        binding.toolbar.getLeftIcon().setOnClickListener {
            finish()
        }
    }

    private fun saveWeightAndHeight() {
        viewModel.saveWeightAndHeight(weightNumber, heightNumber)
    }

    private fun nextScreen() {
        val intent = Intent(this, ResultBMIActivity::class.java)
        startActivity(intent)
    }
}