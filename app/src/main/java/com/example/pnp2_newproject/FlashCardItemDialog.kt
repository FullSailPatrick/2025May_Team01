package com.example.pnp2_newproject

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.core.graphics.drawable.toDrawable

class FlashCardItemDialog(context: Context, private var dialogListener: DialogListener) : AppCompatDialog(context) {
    private lateinit var etItemQuestion : EditText
    private lateinit var etItemAnswer : EditText
    private lateinit var tvSave : TextView
    private lateinit var tvCancel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.flashcard_dialog)

        window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        etItemQuestion = findViewById(R.id.etItemQuestion)!!
        etItemAnswer = findViewById(R.id.etItemAnswer)!!
        tvSave = findViewById(R.id.tvSave)!!
        tvCancel = findViewById(R.id.tvCancel)!!

        // Click listener on Save button to save all data.
        tvSave.setOnClickListener {

            // Take both inputs in different variables from user and add it in FlashCard Items database
            var question = etItemQuestion.text.toString()
            var answer = etItemAnswer.text.toString()

            // Toast to display enter items in edit text
            if (question.isEmpty()) {
                Toast.makeText(context, "Please Enter Q/A", Toast.LENGTH_SHORT).show()
            }

            var item = FlashCardItems(question, answer)
            dialogListener.onAddButtonClicked(item)
            dismiss()
        }

        // On click listener on cancel text to close dialog box
        tvCancel.setOnClickListener {
            cancel()
        }
    }
}