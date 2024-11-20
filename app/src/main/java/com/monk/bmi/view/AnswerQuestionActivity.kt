package com.monk.bmi.view

import android.graphics.text.LineBreaker
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.monk.bmi.R
import com.monk.bmi.databinding.ActivityAnswerQuestionBinding

class AnswerQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        clickEvent()
    }
    private fun setupView() {
        binding.toolbar.setupTitleToolbar(getString(R.string.AnswerQuestion_A_01))
        binding.toolbar.setIconLeft(R.drawable.icon_back)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvContentStart.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.tvContentCentre.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.tvContentEnd.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
    }

    private fun clickEvent() {
        binding.toolbar.getLeftIcon().setOnClickListener {
            finish()
        }
    }
}