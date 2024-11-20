package com.monk.bmi.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.R
import com.monk.bmi.custom_layout.SegmentView
import com.monk.bmi.databinding.FragmentSettingBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.utils.NumSegments
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.SettingViewModel

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel

    var chooseGender = NumSegments.FEMALE.getValue()
    var ageNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

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
        )[SettingViewModel::class.java]
    }

    private fun setupView() {
        binding.svChooseGender.setText(
            NumSegments.FEMALE.getValue(),
            getString(R.string.ChooseGender_A_02)
        )
        binding.svChooseGender.setText(
            NumSegments.MALE.getValue(),
            getString(R.string.ChooseGender_A_03)
        )

        viewModel.getAge().let { age ->
            ageNumber = age
            binding.tvAgeInput.text =
                String.format("%d %s", age, getString(R.string.ChooseAge_A_02))
            binding.seekBar.progress = age
        }

        viewModel.getGender().let {
            if (it) {
                binding.svChooseGender.selectedIndex = NumSegments.MALE.getValue()
            } else {
                binding.svChooseGender.selectedIndex = NumSegments.FEMALE.getValue()
            }
        }
    }

    private fun clickEvent() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvAgeInput.text =
                    String.format("%d %s", progress, getString(R.string.ChooseAge_A_02))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                ageNumber = seekBar.progress

            }
        })

        binding.svChooseGender.onSegmentItemSelectedListener = object :
            SegmentView.OnSegmentItemSelectedListener {
            override fun onSegmentItemSelected(index: Int) {
                chooseGender = index
            }

            override fun onSegmentItemReselected(index: Int) {}
        }

        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        viewModel.saveAge(ageNumber)
        saveChooseGender(chooseGender)
        Toast.makeText(requireContext(), getString(R.string.ResultBMI_A_15), Toast.LENGTH_SHORT)
            .show()
    }

    private fun saveChooseGender(index: Int) {
        // 0 -> female  1-> male
        val isMale = index != 0
        viewModel.saveGender(isMale)
    }
}