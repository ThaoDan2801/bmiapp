package com.monk.bmi.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.monk.bmi.databinding.ActivitySplashBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.utils.setStatusBarColor

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarColor()
        handlesScreenNavigation()


    }

    private fun handlesScreenNavigation() {
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences =
                getSharedPreferences(AppConstants.KEY_BMI_APP_PREFS, Context.MODE_PRIVATE)
            val isFirstTime = sharedPreferences.getBoolean(AppConstants.KEY_IS_FIRST_TIME, true)

            if (isFirstTime) {
                startActivity(Intent(this, ChooseGenderActivity::class.java))
            } else {
                val gender = sharedPreferences.getBoolean(AppConstants.KEY_CHOOSE_GENDER, false)
                val age = sharedPreferences.getInt(AppConstants.KEY_CHOOSE_AGE, -1)

                if ( age != -1) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, ChooseGenderActivity::class.java))
                }
            }
            sharedPreferences.edit().putBoolean(AppConstants.KEY_IS_FIRST_TIME, false).apply()
            finish()
            Log.e("TAG", "handlesScreenNavigation", )

        }, AppConstants.SPLASH_DELAY)
    }
    private fun setStatusBarColor() {
        setStatusBarColor(
            this@SplashActivity, Color.parseColor("#B24592")
        )
    }
}