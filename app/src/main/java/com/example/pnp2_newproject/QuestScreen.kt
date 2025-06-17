package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuestScreen : AppCompatActivity()
{
    //variables for buttons
    private lateinit var backButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.quest_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //connect variables to actual buttons
        backButton = findViewById(R.id.backBtn)
        nextButton = findViewById(R.id.nextBtn)

        //set click listener
        backButton.setOnClickListener()
        {
            val intent = Intent(this, PlayModesScreen::class.java)
            startActivity(intent)
        }
       nextButton.setOnClickListener()
       {
           val intent = Intent(this, GamePlayScreen::class.java)
           startActivity(intent)
       }
        TimerManager.timerFinished.observe(this) {finished ->
            if(finished) {
                Toast.makeText(this,"Time For A Break", Toast.LENGTH_SHORT).show()
            }
        }
    }
}