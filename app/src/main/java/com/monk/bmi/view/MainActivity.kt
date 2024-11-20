package com.monk.bmi.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.monk.bmi.BuildConfig
import com.monk.bmi.R
import com.monk.bmi.databinding.ActivityMainBinding
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.utils.setStatusBarColor
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var actionBarToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        clickEvent()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            BMIViewModelFactory(
                this.getSharedPreferences(
                    AppConstants.KEY_BMI_APP_PREFS,
                    MODE_PRIVATE
                )
            )
        )[MainViewModel::class.java]
    }

    private fun setupView() {
        if (viewModel.getIsNavigateToHistory()) {
            binding.toolbar.setupTitleToolbar(getString(R.string.History_A_01))
            openFragment(HistoryFragment())
            viewModel.updateValueIsNavigate(false)
        } else {
            binding.toolbar.setupTitleToolbar(getString(R.string.Home_A_01))
            openFragment(HomeFragment())
        }

        this@MainActivity.onBackPressedDispatcher.addCallback(this) {
            finish()
        }
        actionBarToggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
    }

    private fun clickEvent() {
        binding.apply {
            toolbar.getLeftIcon().setOnClickListener {
                binding.drawerLayout.openDrawer(binding.navView)
            }

            navView.setNavigationItemSelectedListener { menuItem ->
                var titleName = ""
                when (menuItem.itemId) {
                    R.id.calculate_bmi -> {
                        openFragment(HomeFragment())
                        titleName = getString(R.string.Home_A_01)
                    }

                    R.id.history -> {
                        openFragment(HistoryFragment())
                        titleName = getString(R.string.History_A_01)
                    }

                    R.id.policy -> {
                        openFragment(PolicyFragment())
                        titleName = getString(R.string.Policy_A_01)
                    }

                    R.id.setting -> {
                        openFragment(SettingFragment())
                        titleName = getString(R.string.Setting_A_01)
                    }

                    else -> {
                        shareApplication()
                    }
                }
                binding.toolbar.setupTitleToolbar(titleName)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }

        binding.drawerLayout.setDrawerListener(object : DrawerListener {
            private var last = 0f
            override fun onDrawerSlide(arg0: View, arg1: Float) {
                val opening = arg1 > last
                val closing = arg1 < last
                if (opening) {
                    setStatusBarColor("#B24592")
                } else if (closing) {
                    setStatusBarColor("#F7F7F9")
                } else {
                    setStatusBarColor("#F7F7F9")
                }
                last = arg1
            }

            override fun onDrawerStateChanged(arg0: Int) {}
            override fun onDrawerOpened(arg0: View) {}
            override fun onDrawerClosed(arg0: View) {}
        })
    }

    private fun shareApplication() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage =
                """
                                ${shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID}
                                
                               
                                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            Log.e("TAG", "shareApplication: ${e.message}", )
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun setStatusBarColor(color: String) {
        setStatusBarColor(
            this@MainActivity, Color.parseColor(color)
        )
    }
}