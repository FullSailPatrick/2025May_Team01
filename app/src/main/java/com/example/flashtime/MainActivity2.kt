package org.geeksforgeeks.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flashtime.MainActivity

class SecondActivity : AppCompatActivity() {
    // define the global variable
    // Add button Move to next Activity and previous Activity
    private lateinit var next_button: Button
    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the both Button and textview
        next_button = findViewById(R.id.next_button)
        previous_button = findViewById(R.id.prev_button)

        // add click listener
        next_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called ThirdActivity with the following code.
            val intent = Intent(this, ThirdActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        // add click listener
        previous_button.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called FirstActivity with the following code
            val intent = Intent(this, MainActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
    }
}