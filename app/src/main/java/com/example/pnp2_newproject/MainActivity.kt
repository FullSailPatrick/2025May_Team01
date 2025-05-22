package com.example.pnp2_newproject
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity()
{
    //for the "panels"
    lateinit var panel01 : FrameLayout
    lateinit var panel02 : FrameLayout
    lateinit var panel03 : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        panel01 = findViewById(R.id.panel01)
        panel02 = findViewById(R.id.panel02)
        panel03 = findViewById(R.id.panel03)

        setupPanelInteractions()
    }
}