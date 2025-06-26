package com.example.pnp2_newproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.content.edit

class FlashCardGame : AppCompatActivity() {

    private lateinit var viewModel: FlashCardViewModel
    private lateinit var questionText: TextView
    private lateinit var answerText: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var correctButton: Button
    private lateinit var nextButton: Button
    private lateinit var restartButton: Button
    private lateinit var timerText: TextView

    private var flashcards = listOf<FlashCardItems>()
    private var currentIndex = 0
    private var score = 0
    private var timer: CountDownTimer? = null
    private val totalTime = 60000L // 60 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashcard_game)

        val repository = FlashCardRepository(FlashCardDatabase(this))
        val factory = FlashCardViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[FlashCardViewModel::class.java]

        val button = findViewById<Button>(R.id.ForgeFromFCGame)

        button.setOnClickListener()
        {
            val intent = Intent(this, CreateFlashCardsScreen::class.java)
            startActivity(intent)
            finish()
        }

        // UI components
        questionText = findViewById(R.id.quiz_question_text)
        answerText = findViewById(R.id.quiz_answer_text)
        showAnswerButton = findViewById(R.id.show_answer_button)
        correctButton = findViewById(R.id.correct_button)
        nextButton = findViewById(R.id.next_button)
        restartButton = findViewById(R.id.restart_button)
        timerText = findViewById(R.id.timer_text)

        viewModel.allFlashCardItems().observe(this) { cards ->
            flashcards = cards.shuffled() // shuffle for randomness
            if (flashcards.isNotEmpty()) {
                startQuiz()
            } else {
                Toast.makeText(this, "No flashcards found.", Toast.LENGTH_SHORT).show()
            }
        }

        showAnswerButton.setOnClickListener {
            answerText.text = flashcards[currentIndex].answer
        }

        correctButton.setOnClickListener {
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        }

        nextButton.setOnClickListener {
            nextQuestion()
        }

        restartButton.setOnClickListener {
            restartQuiz()
        }

        val timerText = findViewById<TextView>(R.id.timer_text)

        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerText.text = "Time: ${secondsLeft}s"
            }

            override fun onFinish() {
                val intent = Intent(this@FlashCardGame, CreateFlashCardsScreen::class.java)
                endQuiz()
                Toast.makeText(this@FlashCardGame, "Time’s up!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    object ScoreManager {
        private const val PREF_NAME = "quiz_prefs"
        private const val KEY_HIGH_SCORE = "high_score"

        fun getHighScore(context: Context): Int {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return prefs.getInt(KEY_HIGH_SCORE, 0)
        }

        fun saveHighScore(context: Context, score: Int) {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val currentHigh = getHighScore(context)
            if (score > currentHigh) {
                prefs.edit { putInt(KEY_HIGH_SCORE, score) }
            }
        }
    }


    private fun startQuiz() {
        currentIndex = 0
        score = 0
        timerText.text = "Time: 60s"
        showQuestion()
    }

    private fun showQuestion() {
        if (currentIndex < flashcards.size) {
            val card = flashcards[currentIndex]
            questionText.text = card.question
            answerText.text = ""
        } else {
            endQuiz()
        }
    }

    private fun nextQuestion() {
        currentIndex++
        if (currentIndex < flashcards.size) {
            showQuestion()
        } else {
            endQuiz()
        }
    }

    private fun endQuiz() {
        questionText.text = "All Done!"
        answerText.text = "Score: $score / ${flashcards.size}"

        ScoreManager.saveHighScore(this, score)
        val highScore = ScoreManager.getHighScore(this)
        Toast.makeText(this, "High Score: $highScore", Toast.LENGTH_LONG).show()

        restartButton.visibility = Button.VISIBLE
    }


    private fun restartQuiz() {
        flashcards = flashcards.shuffled()
        restartButton.visibility = Button.GONE
        startQuiz()
    }
}
