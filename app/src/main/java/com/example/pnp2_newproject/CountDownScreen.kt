package com.example.pnp2_newproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.CountDownTimer
import android.widget.TextView

class CountDownScreen : AppCompatActivity()
{
    //code here
    private lateinit var countdownTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.countdown_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //code here
        countdownTextView = findViewById<TextView>(R.id.textView6)

        val countDownTimer = object : CountDownTimer(3000,1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                val secondsRemaining = millisUntilFinished / 1000
                countdownTextView.text = secondsRemaining.toString()
            }

            override fun onFinish()
            {
                countdownTextView.text = "GO!"

                val intent = Intent(this@CountDownScreen, GamePlayScreen::class.java)
                startActivity(intent)
            }
        }
        countDownTimer.start()
    }
}