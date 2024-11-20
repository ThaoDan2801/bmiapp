package com.monk.bmi.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.monk.bmi.R
import com.monk.bmi.databinding.DialogDeleteItemBinding
import com.monk.bmi.model.History
import com.monk.bmi.utils.ConvertDateTime

class DialogDeleteItem {
    private lateinit var binding: DialogDeleteItemBinding
    private lateinit var dialog: Dialog
    private lateinit var dialogResult: DialogResult
    fun onCreateDialog(
        context: Context,
        history: History,
        gender: Boolean
    ) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogDeleteItemBinding.inflate(LayoutInflater.from(context), null, false)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setupView(context, history, gender)
        clickEvent()
        dialog.show()
    }

    private fun setupView(context: Context, history: History, gender: Boolean) {
        binding.tvResult.text = String.format("%.1f", history.bmi)
        val time = history.createdAt ?: 0L
        binding.tvDate.text = ConvertDateTime.convertTime(time)

        if (gender) {
            binding.imgGender.setImageResource(R.drawable.img_male_gender_on)
        } else {
            binding.imgGender.setImageResource(R.drawable.img_female_gender_on)
        }

        val healthyMessage = getBMIColorAndStatus(history.bmi)
        binding.tvComment.text = context.getString(healthyMessage)

        binding.tvMemoBmi.text = history.note
    }

    private fun getBMIColorAndStatus(bmi: Double): Int {
        return when {
            bmi < 17.0 -> (R.string.ResultBMI_A_10)
            bmi < 18.5 -> (R.string.ResultBMI_A_11)
            bmi < 25.0 -> (R.string.ResultBMI_A_12)
            bmi >= 25.0 -> (R.string.ResultBMI_A_13)
            bmi >= 30.0 -> (R.string.ResultBMI_A_14)
            else -> 0
        }
    }

    fun setDialogResult(result: DialogResult) {
        this.dialogResult = result
    }

    private fun clickEvent() {
        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnDelete.setOnClickListener {
            dialogResult.finish()
            dialog.dismiss()
        }
    }

    interface DialogResult {
        fun finish()
    }
}