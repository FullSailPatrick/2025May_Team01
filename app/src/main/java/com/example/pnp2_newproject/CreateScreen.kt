package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateScreen : AppCompatActivity()
{
    //variables for buttons
    private lateinit var backToHomeButton: Button
    private lateinit var HoleInTheWallButton: Button
    private lateinit var HangmanButton: Button
    private lateinit var flashCardsButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.create_screen)

        //variables
        backToHomeButton = findViewById<Button>(R.id.backToHome)
        HoleInTheWallButton = findViewById<Button>(R.id.HoleInTheWall)
        HangmanButton = findViewById<Button>(R.id.Hangman)
        flashCardsButton = findViewById<Button>(R.id.FlashCards)

        //on Click Listeners
        backToHomeButton.setOnClickListener()
        {
            val intent: Intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        //HoleInTheWallButton.setOnClickListener()
        //{
        //    val intent: Intent = Intent(this, HoleInTheWallScreen::class.java)
        //    startActivity(intent)
        //}
        //HangmanButton.setOnClickListener()
        //{
        //    val intent: Intent = Intent(this, HangmanScreen::class.java)
        //    startActivity(intent)
        //}
        //flashCardsButton.setOnClickListener()
        //{
        //    val intent: Intent = Intent(this, FlashCardsScreen::class.java)
        //    startActivity(intent)
        //}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}

