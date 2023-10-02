package com.example.github.ui.theme

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.github.R
import com.google.android.material.switchmaterial.SwitchMaterial

class NightSwitchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_switch)

        val switchTheme = findViewById<SwitchMaterial>(R.id.night_mode)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ThemeViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
            AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}