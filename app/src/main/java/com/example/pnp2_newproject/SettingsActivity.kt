package com.example.pnp2_newproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        supportActionBar?.title = "Settings"

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.idFrameLayout, SettingsFragment())
                .commit()
        }
    }
}