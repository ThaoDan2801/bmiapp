package com.monk.bmi.custom_layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.monk.bmi.databinding.LayoutToolbarBinding

open class ToolbarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: LayoutToolbarBinding

    init {
        initToolBar()
    }

    private fun initToolBar() {
        binding = LayoutToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setupTitleToolbar(text: String) {
        binding.tvTitle.text = text
    }

    fun hideBackIcon() {
        binding.btnIconLeft.visibility = INVISIBLE
    }

    fun showIconRight() {
        binding.btnIconRight.visibility = VISIBLE
    }

    fun setIconLeft(icon: Int) {
        binding.btnIconLeft.setImageResource(icon)
    }

    fun getLeftIcon(): AppCompatImageView {
        return binding.btnIconLeft
    }

    fun getRightIcon(): CardView {
        return binding.btnIconRight
    }
}