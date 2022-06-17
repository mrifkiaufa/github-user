package com.aufa.githubuserapp.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.aufa.githubuserapp.preference.SettingPreferences
import com.aufa.githubuserapp.R
import com.aufa.githubuserapp.model.ThemeViewModel
import com.aufa.githubuserapp.model.ThemeViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        val changeTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val preference = SettingPreferences.getInstance(dataStore)

        val themeViewModel = ViewModelProvider(this,
            ThemeViewModelFactory(preference))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this
        ) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                changeTheme.isChecked = false
            }
        }

        changeTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}