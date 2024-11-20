package com.monk.bmi.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.R
import com.monk.bmi.custom_layout.SegmentView
import com.monk.bmi.databinding.ActivityChooseGenderBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.utils.NumSegments
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.ChooseGenderViewModel

class ChooseGenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseGenderBinding
    private lateinit var viewModel: ChooseGenderViewModel

    var chooseGender = NumSegments.FEMALE.getValue()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseGenderBinding.inflate(layoutInflater)
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
                    Context.MODE_PRIVATE
                )
            )
        )[ChooseGenderViewModel::class.java]
    }

    private fun setupView() {
        binding.segmentView.numSegments = 2
        binding.segmentView.setText(
            NumSegments.FEMALE.getValue(),
            getString(R.string.ChooseGender_A_02)
        )
        binding.segmentView.setText(
            NumSegments.MALE.getValue(),
            getString(R.string.ChooseGender_A_03)
        )
        binding.segmentView.selectedIndex = chooseGender
    }

    private fun clickEvent() {
        binding.btnNext.btn.setOnClickListener {
            saveChooseGender(chooseGender)
            nextScreen()
        }
        binding.segmentView.onSegmentItemSelectedListener = object :
            SegmentView.OnSegmentItemSelectedListener {
            override fun onSegmentItemSelected(index: Int) {
                updateGenderImage(index)
                chooseGender = index
            }

            override fun onSegmentItemReselected(index: Int) {}
        }
    }

    private fun saveChooseGender(index: Int) {
        // 0 -> female  1-> male
        val isMale = index != 0
        viewModel.saveGender(isMale)
    }

    private fun updateGenderImage(index: Int) {
        if (index == 0) {
            binding.imgFemale.setImageResource(R.drawable.img_female_gender_on)
            binding.imgMale.setImageResource(R.drawable.img_male_gender_off)
        } else {
            binding.imgFemale.setImageResource(R.drawable.img_female_gender_off)
            binding.imgMale.setImageResource(R.drawable.img_male_gender_on)
        }
    }

    private fun nextScreen() {
        val intent = Intent(this, ChooseAgeActivity::class.java)
        startActivity(intent)
    }
}