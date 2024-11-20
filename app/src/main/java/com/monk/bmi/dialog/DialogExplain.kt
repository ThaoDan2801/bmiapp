package com.monk.bmi.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.monk.bmi.databinding.DialogExplainBinding

class DialogExplain {
    private lateinit var binding: DialogExplainBinding
    private lateinit var dialog: Dialog
    fun onCreateDialog(
        context: Context,
    ) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogExplainBinding.inflate(LayoutInflater.from(context), null, false)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setupView()
        clickEvent()
        dialog.show()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvContent.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
    }

    private fun clickEvent() {
        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
    }
}