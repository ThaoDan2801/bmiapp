package com.monk.bmi.adapter

import com.monk.bmi.model.History

interface OnHistoryItemClickListener {
    fun onClick(history: History)
}