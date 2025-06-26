package com.example.pnp2_newproject

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FlashCardAdapter(
    private val viewModel: FlashCardViewModel
) : ListAdapter<FlashCardItems, FlashCardAdapter.FlashCardViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return FlashCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.txtItemAnswer.text = "Answer: ${currentItem.answer}"
        holder.txtItemQuestion.text = "Question: ${currentItem.question}"

        holder.ibDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Flashcard")
                .setMessage("Are you sure you want to delete this flashcard?")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.delete(currentItem)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        holder.ibEdits.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.dialog_edit_flashcard, null)

            val editQuestion = dialogView.findViewById<EditText>(R.id.edit_question_input)
            val editAnswer = dialogView.findViewById<EditText>(R.id.edit_answer_input)

            editQuestion.setText(currentItem.question)
            editAnswer.setText(currentItem.answer)

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Edit Flashcard")
                .setView(dialogView)
                .setPositiveButton("Save") { dialog, _ ->
                    val updatedItem = currentItem.copy(
                        question = editQuestion.text.toString(),
                        answer = editAnswer.text.toString()
                    ).apply {
                        id = currentItem.id
                    }
                    viewModel.update(updatedItem)

                    //Show confirmation
                    Toast.makeText(holder.itemView.context, "Flashcard updated", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    inner class FlashCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItemQuestion: TextView = itemView.findViewById(R.id.txtItemQuestion)
        val txtItemAnswer: TextView = itemView.findViewById(R.id.txtItemAnswer)
        val ibDelete: ImageButton = itemView.findViewById(R.id.ibDelete)
        val ibEdits: ImageButton = itemView.findViewById(R.id.ibEdits)
    }

    class DiffCallback : DiffUtil.ItemCallback<FlashCardItems>() {
        override fun areItemsTheSame(oldItem: FlashCardItems, newItem: FlashCardItems): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FlashCardItems, newItem: FlashCardItems): Boolean {
            return oldItem == newItem
        }
    }
}
