package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monk.bmi.utils.AppConstants

class ChooseGenderViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _genderLiveData = MutableLiveData<Boolean>()
    fun saveGender(isMale: Boolean) {
        _genderLiveData.value = isMale
        sharedPreferences.edit().putBoolean(AppConstants.KEY_CHOOSE_GENDER, isMale).apply()
    }
}