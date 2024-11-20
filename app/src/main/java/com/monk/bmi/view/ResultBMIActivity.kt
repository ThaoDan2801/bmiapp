package com.monk.bmi.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.R
import com.monk.bmi.databinding.ActivityResultBmiBinding
import com.monk.bmi.dialog.DialogExplain
import com.monk.bmi.model.HistoryDatabaseHelper
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.ResultBMIViewModel

class ResultBMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBmiBinding
    private lateinit var viewModel: ResultBMIViewModel
    private lateinit var historyDatabaseHelper: HistoryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        clickEvent()
        addEvents()
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
        )[ResultBMIViewModel::class.java]
        historyDatabaseHelper = HistoryDatabaseHelper(this)
    }

    private fun clickEvent() {
        binding.btnSave.setOnClickListener {
            saveData()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.toolbar.getRightIcon().setOnClickListener {
            nextScreen()
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
            )
        }
        binding.btnInfo.setOnClickListener {
            showDialogInfo()
        }
    }

    private fun showDialogInfo() {
        val dialog = DialogExplain()
        dialog.onCreateDialog(this)
    }

    private fun nextScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        startActivity(intent)
        finish()
    }

    private fun saveData() {
        viewModel.saveIsNavigateToHistory(true)
        historyDatabaseHelper.insertHistory(viewModel.saveValueBMI(binding.editNote.text.toString()))
        Toast.makeText(this, getString(R.string.ResultBMI_A_15), Toast.LENGTH_SHORT).show()
    }

    private fun setupView() {
        binding.apply {
            binding.toolbar.hideBackIcon()
            binding.toolbar.showIconRight()
            binding.toolbar.setupTitleToolbar(getString(R.string.ResultBMI_A_01))

            if (viewModel.getGender()) {
                binding.imgGender.setImageResource(R.drawable.img_male_gender_on)
            } else {
                binding.imgGender.setImageResource(R.drawable.img_female_gender_on)
            }

            editNote.setText("")
            tvWeight.text =
                String.format("%.1f %s", viewModel.getWeight(), getString(R.string.ResultBMI_A_03))
            tvHeight.text =
                String.format("%.1f %s", viewModel.getHeight(), getString(R.string.ResultBMI_A_05))
            tvResult.text = String.format("%.1f", viewModel.calculationBMI())

            val (color, healthyMessage) = getBMIColorAndStatus(viewModel.calculationBMI())
            tvResult.setTextColor(Color.parseColor(color))
            tvComment.text = healthyMessage
        }
    }

    private fun addEvents() {
        onBackPressedDispatcher.addCallback(this) {
            nextScreen()
        }
    }

    private fun getBMIColorAndStatus(bmi: Double): Pair<String, String> {
        val colorCode01 = "#42FF00"
        val colorCode02 = "#FF0000"
        return when {
            bmi < 17.0 -> Pair(colorCode01, getString(R.string.ResultBMI_A_10))
            bmi < 18.5 -> Pair(colorCode01, getString(R.string.ResultBMI_A_11))
            bmi < 25.0 -> Pair(colorCode01, getString(R.string.ResultBMI_A_12))
            bmi >= 25.0 -> Pair(colorCode02, getString(R.string.ResultBMI_A_13))
            bmi >= 30.0 -> Pair(colorCode02, getString(R.string.ResultBMI_A_14))
            else -> Pair("", "")
        }
    }
}