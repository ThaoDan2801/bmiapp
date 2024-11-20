package com.monk.bmi.utils

import android.app.Activity
import android.view.WindowManager

fun Activity.setStatusBarColor(activity: Activity, color: Int) {
    val window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}