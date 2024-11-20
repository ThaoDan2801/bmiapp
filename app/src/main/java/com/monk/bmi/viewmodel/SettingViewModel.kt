package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monk.bmi.utils.AppConstants

class SettingViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _genderLiveData = MutableLiveData<Boolean>()
    private val _ageLiveData = MutableLiveData<Int>()

    init {
        val isMale = sharedPreferences.getBoolean(AppConstants.KEY_CHOOSE_GENDER, false)
        _genderLiveData.value = isMale

        val ageNumber = sharedPreferences.getInt(AppConstants.KEY_CHOOSE_AGE, 0)
        _ageLiveData.value = ageNumber
    }

    fun getGender(): Boolean {
        return _genderLiveData.value ?: false
    }

    fun getAge(): Int {
        return _ageLiveData.value ?: 0
    }

    fun saveGender(isMale: Boolean) {
        _genderLiveData.value = isMale
        sharedPreferences.edit().putBoolean(AppConstants.KEY_CHOOSE_GENDER, isMale).apply()
    }

    fun saveAge(age: Int) {
        _ageLiveData.value = age
        sharedPreferences.edit().putInt(AppConstants.KEY_CHOOSE_AGE, age).apply()
    }
}