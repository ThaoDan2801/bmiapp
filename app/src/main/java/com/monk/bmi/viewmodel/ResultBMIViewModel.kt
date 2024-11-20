package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monk.bmi.model.History
import com.monk.bmi.utils.AppConstants
import kotlin.math.pow

class ResultBMIViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _genderLiveData = MutableLiveData<Boolean>()
    private val _weightLiveData = MutableLiveData<Float>()
    private val _heightLiveData = MutableLiveData<Float>()
    private val _isNavigateToHistoryLiveData = MutableLiveData<Boolean>()

    init {
        val isMale = sharedPreferences.getBoolean(AppConstants.KEY_CHOOSE_GENDER, false)
        _genderLiveData.value = isMale

        val weightNumber = sharedPreferences.getFloat(AppConstants.KEY_CHOOSE_WEIGHT, 0f)
        _weightLiveData.value = weightNumber

        val heightNumber = sharedPreferences.getFloat(AppConstants.KEY_CHOOSE_HEIGHT, 0f)
        _heightLiveData.value = heightNumber

    }

    fun getGender(): Boolean {
        return _genderLiveData.value ?: false
    }

    fun getWeight(): Float {
        return _weightLiveData.value ?: 0f
    }

    fun getHeight(): Float {
        return _heightLiveData.value ?: 0f
    }

    fun calculationBMI(): Double {
        val doubleHeight = getHeight().toDouble() / 100
        return (getWeight() / doubleHeight.pow(2))
    }

    fun saveValueBMI(text: String): History {
        return History(
            id = 0,
            height = getWeight(),
            weight = getHeight(),
            bmi = calculationBMI(),
            note = text,
            createdAt = System.currentTimeMillis()
        )
    }

    fun saveIsNavigateToHistory(isNavigateToHistory: Boolean) {
        _isNavigateToHistoryLiveData.value = isNavigateToHistory
        sharedPreferences.edit()
            .putBoolean(AppConstants.KEY_NAVIGATE_TO_HISTORY, isNavigateToHistory).apply()
    }
}