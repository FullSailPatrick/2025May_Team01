package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayModesScreen : AppCompatActivity()
{
    //variables for panels
    private lateinit var panel01 : FrameLayout
    private lateinit var panel02 : FrameLayout
    private lateinit var panel03 : FrameLayout

    //variables for buttons
    private lateinit var backButton: Button
    private lateinit var questButton: Button
    private lateinit var freePlayButton: Button
    private lateinit var flashButton: Button

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
        flashButton = findViewById<Button>(R.id.flashModeBtn)

        //set click listener for buttons
        backButton.setOnClickListener()
        {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
       questButton.setOnClickListener()
       {
           val intent = Intent(this, QuestScreen::class.java)
           startActivity(intent)
       }
       //freePlayButton.setOnClickListener()
       //{
       //    val intent = Intent(this, FreePlayScreen::class.java)
       //    startActivity(intent)
       //}
       //flashButton.setOnClickListener()
       //{
       //    val intent = Intent(this, FlashScreen::class.java)
       //    startActivity(intent)
       //}

        //connect variables to actual panels (clean up naming convention here)
        panel01 = findViewById(R.id.panel01)
        panel02 = findViewById(R.id.panel02)
        panel03 = findViewById(R.id.panel03)


        TimerManager.timerFinished.observe(this) {finished ->
            if(finished) {
                Toast.makeText(this,"Time For A Break", Toast.LENGTH_SHORT).show()
            }
        }
    }
}