package com.example.pnp2_newproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs
import android.os.CountDownTimer
import androidx.core.view.isVisible
import kotlinx.coroutines.delay

//include gesture detector
class Level01GamePlayScreen : AppCompatActivity(), GestureDetector.OnGestureListener {

    //variables
    private lateinit var THEFLASHCARD: androidx.cardview.widget.CardView
    private lateinit var FlashCardText: TextView
    private lateinit var gestureDetector: GestureDetector
    private lateinit var timer: CountDownTimer
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100
    var correctAnswers = 0
    var TotalAnswers = 0
    var index = 0
    var showingQuestion = true
    private lateinit var quitButton: Button

    //the flashCard "template" being used
    val FlashCardQuestionsAnswers = listOf (
        FlashCard("1 + 1?", "2"),
        FlashCard("What is the smallest positive integer that is both a square and a cube?", "1"),
        FlashCard("What's 100 / 10?", "10"),
        FlashCard("What is the sum of the interior angles of a 20-sided polygon?", "3,240 degrees"),
        FlashCard("What's 1 x 1?", "2"),
        FlashCard("Can a function be both even and odd?", "Yes, the zero function. f(x)=0"),
        FlashCard("What's 2 x 2?",  "4"),
        FlashCard("On Feb 12, 1809 Abraham Lincoln was born. How old was his mother?",  "25 years old")
    )

    //keeps track of what index you're on
    fun goToNextFlashCard() {

        var FlashCardHolder = FlashCardQuestionsAnswers[index]
        index += 1

        if(index == FlashCardQuestionsAnswers.size)
        {
            val intent = Intent(this, PlayerResultsScreen::class.java)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalAnswers", TotalAnswers)
            startActivity(intent)
            finish()
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
        setContentView(R.layout.lvl01gameplay_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        quitButton = findViewById(R.id.quitBtn)
        quitButton.setOnClickListener()
        {
            val intent = Intent(this, QuestScreen::class.java)
            startActivity(intent)
        }
        //timer code here
        val timerTextView = findViewById<TextView>(R.id.timerTextView)

        timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUnitlFinished: Long) {
                val secondsLeft = millisUnitlFinished / 1000
                timerTextView.text = "$secondsLeft"
            }

            override fun onFinish() {
                val intent = Intent(this@Level01GamePlayScreen, PlayerResultsScreen::class.java)
                intent.putExtra("correctAnswers", correctAnswers)
                intent.putExtra("totalAnswers", TotalAnswers)
                startActivity(intent)
                finish()
            }
        }
        timer.start()

        // initialize the gesture detector variable
        gestureDetector = GestureDetector(this, this)

        //connect .xml id to new variables
        THEFLASHCARD = findViewById<androidx.cardview.widget.CardView>(R.id.FlashCard)
        FlashCardText = findViewById<TextView>(R.id.flashCardQuestion)

        val textString = FlashCardText.text.toString()
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
        TimerManager.timerFinished.observe(this) {finished ->
            if(finished) {
                Toast.makeText(this,"Time For A Break", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
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
                            FlashCardText.isVisible = false
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
                                        FlashCardText.isVisible = true
                                    }
                            TotalAnswers++
                            goToNextFlashCard()
                            showingQuestion = true
                        }
                        else {
                            LoadQuestion()
                            LoadAnswer()
                            FlashCardText.isVisible = false
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
                                    FlashCardText.isVisible = true
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


