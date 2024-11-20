package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monk.bmi.utils.AppConstants

class ChooseWeightAndHeightViewModel(private val sharedPreferences: SharedPreferences) :
    ViewModel() {
    private val _genderLiveData = MutableLiveData<Boolean>()
    private val _weightLiveData = MutableLiveData<Float>()
    private val _heightLiveData = MutableLiveData<Float>()

    init {
        val isMale = sharedPreferences.getBoolean(AppConstants.KEY_CHOOSE_GENDER, false)
        _genderLiveData.value = isMale
    }

    fun getGender(): Boolean {
        return _genderLiveData.value ?: false
    }

    fun saveWeightAndHeight(weight: Float, height: Float) {
        _weightLiveData.value = weight
        _heightLiveData.value = height
        sharedPreferences.edit().putFloat(AppConstants.KEY_CHOOSE_WEIGHT, weight).apply()
        sharedPreferences.edit().putFloat(AppConstants.KEY_CHOOSE_HEIGHT, height).apply()
    }
}