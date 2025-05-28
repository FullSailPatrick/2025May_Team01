package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager

class HomeScreen : AppCompatActivity()
{
    //variable
    private lateinit var settingsBtn: Button
    private lateinit var createBtn: Button
    private lateinit var favoritesBtn: Button
    private lateinit var playBtn: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        //theme stuff
       val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        when (prefs.getString("day_night", "system")){
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //connect variables to actual buttons
        createBtn = findViewById(R.id.create_button)
        settingsBtn = findViewById(R.id.settings_button)
        favoritesBtn = findViewById(R.id.favorites_button)
        playBtn = findViewById(R.id.play_button)

        //set click listener
        createBtn.setOnClickListener()
        {
            val intent: Intent = Intent(this, CreateScreen::class.java)
            startActivity(intent)
        }
        settingsBtn.setOnClickListener()
        {
            val intent: Intent = (Intent(this, SettingsScreen::class.java))
            startActivity(intent)
        }
       //favoritesBtn.setOnClickListener()
       //{
       //    val intent: Intent = (Intent(this, FavoritesScreen::class.java))
       //    startActivity(intent)
        //}
        playBtn.setOnClickListener()
        {
            val intent: Intent = (Intent(this, PlayModesScreen::class.java))
            startActivity(intent)
        }
    }
}