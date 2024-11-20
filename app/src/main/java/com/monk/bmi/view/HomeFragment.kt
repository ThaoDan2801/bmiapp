package com.monk.bmi.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.R
import com.monk.bmi.databinding.FragmentHomeBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.HomeViewModel
import kotlin.math.roundToInt

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    var weightNumber = 0f
    var heightNumber = 0f
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViewModel()
        setupView()
        clickEvent()
        return binding.root
    }

    private fun setupViewModel() {
        val sharedPreferences = requireContext().applicationContext.getSharedPreferences(
            AppConstants.KEY_BMI_APP_PREFS,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(
            this,
            BMIViewModelFactory(
                sharedPreferences
            )
        )[HomeViewModel::class.java]
    }

    private fun setupView() {
        binding.seekBarWeight.max = 100
        binding.seekBarHeight.max = 200

        if (viewModel.getGender()) {
            binding.imgGender.setImageResource(R.drawable.img_male_gender_on)
        } else {
            binding.imgGender.setImageResource(R.drawable.img_female_gender_on)
        }

        viewModel.getWeight().let { weight ->
            binding.tvWeight.setText(weight.toString())
            binding.seekBarWeight.progress = weight.roundToInt()
        }

        viewModel.getHeight().let { height ->
            binding.tvHeight.setText(height.toString())
            binding.seekBarHeight.progress = height.roundToInt()
        }
    }

    private fun clickEvent() {
        weightNumber = binding.tvWeight.text.toString().toFloat()
        heightNumber = binding.tvHeight.text.toString().toFloat()

        binding.apply {
            btnBack.setOnClickListener {
                if (weightNumber == 0f || heightNumber == 0f) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.ChooseWeightAndHeight_A_07),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    saveWeightAndHeight()
                    startActivity(Intent(requireContext(), ResultBMIActivity::class.java))
                }
            }
            layoutQA.setOnClickListener {
                startActivity(Intent(requireContext(), AnswerQuestionActivity::class.java))
            }
        }

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
    }

    private fun saveWeightAndHeight() {
        viewModel.saveWeightAndHeight(weightNumber, heightNumber)
    }
}