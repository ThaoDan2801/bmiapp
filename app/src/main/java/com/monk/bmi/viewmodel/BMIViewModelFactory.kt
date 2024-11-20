package com.monk.bmi.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BMIViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseGenderViewModel::class.java)) {
            return ChooseGenderViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(ChooseAgeViewModel::class.java)) {
            return ChooseAgeViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(ChooseWeightAndHeightViewModel::class.java)) {
            return ChooseWeightAndHeightViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(ResultBMIViewModel::class.java)) {
            return ResultBMIViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(sharedPreferences) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
