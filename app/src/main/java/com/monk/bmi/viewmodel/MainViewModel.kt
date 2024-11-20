package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monk.bmi.utils.AppConstants

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _isNavigateToHistoryLiveData = MutableLiveData<Boolean>()

    init {
        val isNavigateToHistory =
            sharedPreferences.getBoolean(AppConstants.KEY_NAVIGATE_TO_HISTORY, false)
        _isNavigateToHistoryLiveData.value = isNavigateToHistory
    }

    fun getIsNavigateToHistory(): Boolean {
        return _isNavigateToHistoryLiveData.value ?: false
    }

    fun updateValueIsNavigate(isNavigateToHistory: Boolean) {
        _isNavigateToHistoryLiveData.value = isNavigateToHistory
        sharedPreferences.edit()
            .putBoolean(AppConstants.KEY_NAVIGATE_TO_HISTORY, isNavigateToHistory).apply()
    }
}
