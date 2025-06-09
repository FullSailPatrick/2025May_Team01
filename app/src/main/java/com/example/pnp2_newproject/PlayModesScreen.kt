package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayModesScreen : AppCompatActivity()
{
    //variables for buttons
    private lateinit var backButton: Button
    private lateinit var questButton: Button
    private lateinit var freePlayButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.playmodes_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //connect variables to actual buttons
        backButton = findViewById<Button>(R.id.backBtn)
        questButton = findViewById<Button>(R.id.questModeBtn)
        freePlayButton = findViewById<Button>(R.id.freePlayModeBtn)

        //set click listener for buttons
        backButton.setOnClickListener()
        {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

       questButton.setOnClickListener()
       {
           questButton.animate()
               .setDuration(1000)
               .rotationYBy(360f)
               .withEndAction {
                   val intent = Intent(this, QuestScreen::class.java)
                   startActivity(intent)
               }
       }

       freePlayButton.setOnClickListener()
       {
           freePlayButton.animate()
               .setDuration(1000)
               .rotationYBy(360f)
               .withEndAction {
                   //val intent = Intent(this, FreePlayScreen::class.java)
                   //startActivity(intent)
               }
       }
    }
}