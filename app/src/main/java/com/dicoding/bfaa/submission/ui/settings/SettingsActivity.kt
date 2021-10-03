package com.dicoding.bfaa.submission.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bfaa.submission.databinding.ActivitySettingsBinding
import com.dicoding.bfaa.submission.helper.ViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(SettingsViewModel::class.java)

        viewModel.getThemeSettings().observe(this, { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        })

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            switchTheme.isChecked = isChecked
            viewModel.saveThemeSettings(isChecked)
        }
    }
}