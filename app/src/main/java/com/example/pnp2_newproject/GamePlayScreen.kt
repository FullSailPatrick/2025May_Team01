package com.example.pnp2_newproject

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//include gesture detector
class GamePlayScreen : AppCompatActivity(), GestureDetector.OnGestureListener  {

    //variables
    private lateinit var THEFLASHCARD: androidx.cardview.widget.CardView
    private lateinit var Question:TextView
    private lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    //declare functions to load a question / answer from QuestionAnswer Class (i.e. question replaces "question here" on FlashCard)
    fun LoadQuestion(){
        Question.setText(QuestionAnswer.questions[2])
    }
    fun LoadAnswer(){
        Question.setText(QuestionAnswer.answers[2])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.gameplay_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initialize the gesture detector variable
        gestureDetector = GestureDetector(this, this)

        //connect .xml id to new variables
        THEFLASHCARD = findViewById<androidx.cardview.widget.CardView>(R.id.FlashCard)
        Question = findViewById<TextView>(R.id.flashCardQuestion)

        //call function
        LoadQuestion()

        //flip flash card
        THEFLASHCARD.setOnClickListener()
        {
            THEFLASHCARD.animate()
                .setDuration(1000)
                .rotationYBy(360f)
                .withEndAction {
                    //call load answer
                    LoadAnswer()
                }
        }
    }

    //override this method to recognize touch event
    override fun onTouchEvent(e: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {
        return
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        return
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

}