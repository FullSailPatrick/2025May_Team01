package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuestScreen : AppCompatActivity()
{
    //variables for buttons
    private lateinit var backButton: Button
    private lateinit var lvl01Button: Button
    private lateinit var lvl02Button: Button
    private lateinit var bossButton: Button

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
        lvl01Button = findViewById<Button>(R.id.lvl01Btn)
        lvl02Button = findViewById<Button>(R.id.lvl02Btn)
        bossButton = findViewById<Button>(R.id.BossBtn)

        //set click listener
        backButton.setOnClickListener()
        {
            val intent = Intent(this, PlayModesScreen::class.java)
            startActivity(intent)
        }
        //lvl01Button.setOnClickListener()
        //{
        //
        //}
        //lvl02Button.setOnClickListener()
        //{
        //
        //}
        bossButton.setOnClickListener()
        {
            val intent = Intent(this, CountDownScreen::class.java)
            startActivity(intent)
        }
    }
}