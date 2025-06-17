package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.util.Timer

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePref = findPreference<ListPreference>("day_night")
        themePref?.setOnPreferenceChangeListener{_,newValue ->
            when(newValue) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            true
        }


        val timerPref = findPreference<ListPreference>("break_timer")
        timerPref?.setOnPreferenceChangeListener{_,timerVal ->
            val selectedValue = timerVal as String
            val durationInMinutes = when (selectedValue) {
                "0" -> 0
                "1" -> 15
                "2" -> 30
                "3" -> 45
                "4" -> 60
                "5" -> 1
                else -> 0
            }
            if(durationInMinutes > 0) {
                TimerManager.startTimer(durationInMinutes * 60)
            }
            else{
                TimerManager.cancelTimer()
            }
            true
        }
    }

}