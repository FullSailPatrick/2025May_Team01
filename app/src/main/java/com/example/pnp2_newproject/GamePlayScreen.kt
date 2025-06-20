package com.example.pnp2_newproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs
import android.os.CountDownTimer

//include gesture detector
class GamePlayScreen : AppCompatActivity(), GestureDetector.OnGestureListener {

    //variables
    private lateinit var THEFLASHCARD: androidx.cardview.widget.CardView
    private lateinit var FlashCardText: TextView
    private lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100
    var correctAnswers = 0
    var TotalAnswers = 0

    var index = 0
    var showingQuestion = true
    //keeps track of what index you're on --> I NEED TO FIGURE THIS PART OUT!!!!
    fun goToNextFlashCard() {

        var FlashCardHolder = FlashCardQuestionsAnswers[index]
        index += 1

        if(index == FlashCardQuestionsAnswers.size)
        {
            val intent = Intent(this, PlayerResultsScreen::class.java)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalAnswers", TotalAnswers)
            startActivity(intent)
        }
        else{
            LoadQuestion()
        }
    }

    //declare functions to load a question / answer from QuestionAnswer Class (i.e. question replaces "question here" on FlashCard)
        fun LoadQuestion() {
            FlashCardText.setText(FlashCardQuestionsAnswers[index].questions)
        }

        fun LoadAnswer() {
            FlashCardText.setText(FlashCardQuestionsAnswers[index].answers)
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

        //timer code here
        val timerTextView = findViewById<TextView>(R.id.timerTextView)

        val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUnitlFinished: Long) {
                val secondsLeft = millisUnitlFinished / 1000
                timerTextView.text = "Time Remaining: $secondsLeft"

            }
            override fun onFinish() {
                val intent = Intent(this@GamePlayScreen, PlayerResultsScreen::class.java)
                intent.putExtra("correctAnswers", correctAnswers)
                intent.putExtra("totalAnswers", TotalAnswers)
                startActivity(intent)
            }
        }
        timer.start()

        // initialize the gesture detector variable
        gestureDetector = GestureDetector(this, this)

        //connect .xml id to new variables
        THEFLASHCARD = findViewById<androidx.cardview.widget.CardView>(R.id.FlashCard)
        FlashCardText = findViewById<TextView>(R.id.flashCardQuestion)

        val textString = FlashCardText.text.toString()
        //var showingQuestion = true
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
                    if(showingQuestion) {
                        LoadAnswer()
                    }
                    else{
                        LoadQuestion()
                    }
                    showingQuestion = !showingQuestion
                }
        }
    }

    //override this method to recognize touch event
    override fun onTouchEvent(e: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(e)) {
            true
        }
        else {
            super.onTouchEvent(e)
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
        try {
            val diffY = e2.y - e1!!.y
            val diffX = e2.x - e1!!.x

            if (abs(diffX) > abs(diffY)) {

                //horizontal swipe
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        showToast("swipe: L --> R")
                    } else {
                        showToast("swipe: R --> L")
                    }
                    return true
                }
            } else {
                    //vertical swipe
                    if (abs(diffY) > swipeThreshold && abs(velocityY) > swipeVelocityThreshold) {
                        if (diffY > 0) {
                            //make card slide down
                            THEFLASHCARD.animate()
                                    .setDuration(1000)
                                    .yBy(300f)
                                    //resets the flash card
                                    .withEndAction{
                                        THEFLASHCARD.animate()
                                            .alpha(1f)
                                            .rotation(0f)
                                            .rotationXBy(0f)
                                            .rotationYBy(0f)
                                            .scaleX(1f)
                                            .scaleY(1f)
                                            .translationX(0f)
                                            .translationY(0f)
                                            .setDuration(0)
                                            .start()
                                    }
                            TotalAnswers++
                            goToNextFlashCard()
                            showingQuestion = true
                        }
                        else {
                            LoadQuestion()
                            LoadAnswer()
                            //make card slide up
                            THEFLASHCARD.animate()
                                .setDuration(1000)
                                .yBy(-300f)
                                //resets the flash card
                                .withEndAction{
                                    THEFLASHCARD.animate()
                                        .alpha(1f)
                                        .rotation(0f)
                                        .rotationXBy(0f)
                                        .rotationYBy(0f)
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .translationX(0f)
                                        .translationY(0f)
                                        .setDuration(0)
                                        .start()
                                    }
                            correctAnswers++
                            TotalAnswers++
                            goToNextFlashCard()
                            showingQuestion = true
                        }
                        return true
                    }
                }

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return false
    }
    //define a method to show a toast
        private fun showToast (message:String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
