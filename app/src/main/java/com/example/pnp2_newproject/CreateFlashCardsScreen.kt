package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateFlashCardsScreen : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.create_flashcards_screen)

        val imageButton = findViewById<ImageButton>(R.id.CreateFromFlashCards)
        val buttonView = findViewById<Button>(R.id.HomeFromFlashCards)

        buttonView.setOnClickListener()
        {
            val intent: Intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        imageButton.setOnClickListener()
        {
            val intent: Intent = Intent(this, CreateScreen::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        TimerManager.timerFinished.observe(this) {finished ->
            if(finished) {
                Toast.makeText(this,"Time For A Break", Toast.LENGTH_SHORT).show()
            }
        }
    }

}