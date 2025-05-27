package com.example.flashtime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pnp2_newproject.MainActivity
import com.example.pnp2_newproject.R

class MainActivity2 : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val imageButton = findViewById<ImageButton>(R.id.HomeFromCreate)
        val buttonView = findViewById<Button>(R.id.FlashCards)

        buttonView.setOnClickListener()
        {
            val intent: Intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        imageButton.setOnClickListener()
        {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}

