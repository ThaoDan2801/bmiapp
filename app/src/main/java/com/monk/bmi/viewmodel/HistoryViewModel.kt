package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monk.bmi.utils.AppConstants

class HistoryViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _genderLiveData = MutableLiveData<Boolean>()

    init {
        val isMale = sharedPreferences.getBoolean(AppConstants.KEY_CHOOSE_GENDER, false)
        _genderLiveData.value = isMale
    }

    fun getGender(): Boolean {
        return _genderLiveData.value ?: false
    }
}