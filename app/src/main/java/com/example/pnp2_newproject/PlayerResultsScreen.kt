package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayerResultsScreen : AppCompatActivity()
{
    //variables for buttons
    private lateinit var backButton: Button
    private lateinit var homeButton: Button
    private lateinit var playAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.player_results_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //display score
        val correct = intent.getIntExtra("correctAnswers", 0)
        val total = intent.getIntExtra("totalAnswers", 0)

        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "$correct out of $total"

        //connect variables to actual buttons
        backButton = findViewById(R.id.backBtn)
        homeButton = findViewById(R.id.homeBtn)
        playAgainButton = findViewById(R.id.playAgainBtn)

        //set click listener
        backButton.setOnClickListener()
        {
            val intent = Intent(this, PlayModesScreen::class.java)
            startActivity(intent)
        }
       homeButton.setOnClickListener()
       {
           val intent = Intent(this, HomeScreen::class.java)
           startActivity(intent)
       }
        playAgainButton.setOnClickListener()
        {
            val intent = Intent(this, QuestScreen::class.java)
            startActivity(intent)
        }


        TimerManager.timerFinished.observe(this) {finished ->
            if(finished) {
                Toast.makeText(this,"Time For A Break", Toast.LENGTH_SHORT).show()
            }
        }
    }
}