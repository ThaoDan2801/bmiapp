package com.monk.bmi.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monk.bmi.R
import com.monk.bmi.databinding.ItemHistoryBmiBinding
import com.monk.bmi.model.History
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.utils.ConvertDateTime

class HistoryAdapter(
    private var listHistory: List<History>,
    private val callback: OnHistoryItemClickListener,
    private val sharedPreferences: SharedPreferences,
    val context: Context
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHistoryBmiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgGender = binding.imgGender
        fun bind(history: History, context: Context) {
            binding.tvResult.text = String.format("%.1f", history.bmi)
            binding.tvDate.text = history.createdAt.toString()
            val time = history.createdAt ?: 0L
            binding.tvDate.text = ConvertDateTime.convertTime(time)
            val healthyMessage = getBMIColorAndStatus(history.bmi)
            binding.tvComment.text = context.getString(healthyMessage)
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBmiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listHistory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listHistory[position].let {
            holder.bind(it, context)
        }
        holder.itemView.setOnClickListener {
            listHistory[position].let {
                callback.onClick(it)
            }
        }
        val valueGender = sharedPreferences.getBoolean(AppConstants.KEY_CHOOSE_GENDER, false)
        val imgGender = holder.imgGender
        if (valueGender) {
            imgGender.setImageResource(R.drawable.img_male_gender_on)
        } else {
            imgGender.setImageResource(R.drawable.img_female_gender_on)
        }
    }

    fun refreshDataItemHistory(newListHistory: List<History>) {
        listHistory = newListHistory
        notifyDataSetChanged()
    }
}