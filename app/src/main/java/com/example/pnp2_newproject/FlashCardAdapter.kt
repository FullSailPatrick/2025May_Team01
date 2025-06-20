package com.example.pnp2_newproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlashCardAdapter(var list: List<FlashCardItems>, private var viewModel: FlashCardViewModel) :
    RecyclerView.Adapter<FlashCardAdapter.FlashCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard, parent, false)
        return FlashCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        val currentItem = list[position]
        holder.txtItemAnswer.text = "Answer: ${currentItem.answer}"
        holder.txtItemQuestion.text = "Question: ${currentItem.question}"
        holder.ibDelete.setOnClickListener {
            viewModel.delete(currentItem)
        }

        // Show question and answer only for the last item OR on click
//        if (position == list.size - 1) {
//            holder.txtItemQuestion.visibility = View.VISIBLE
//            holder.txtItemAnswer.visibility = View.VISIBLE
//        } else {
//            holder.txtItemQuestion.visibility = View.GONE
//            holder.txtItemAnswer.visibility = View.GONE
//        }

        holder.txtItemQuestion.setOnClickListener {
            holder.txtItemQuestion.visibility = View.VISIBLE
            holder.txtItemQuestion.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = list.size

    // Inner class for ViewHolder with findViewById
    inner class FlashCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItemQuestion: TextView = itemView.findViewById(R.id.txtItemQuestion)
        val txtItemAnswer: TextView = itemView.findViewById(R.id.txtItemAnswer)
        val ibDelete: ImageButton = itemView.findViewById(R.id.ibDelete)
    }
}