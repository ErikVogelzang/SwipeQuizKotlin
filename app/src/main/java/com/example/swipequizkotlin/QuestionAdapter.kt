package com.example.swipequizkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionAdapter(private val questions: MutableList<Question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>()  {

    private var removedPosition: Int = 0
    private var removedItem: Question = Question("", false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        )
    }

    fun checkAnswer(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (getFalseOrTrueSwipe(direction) == questions[viewHolder.adapterPosition].answer) {
            removeItem(viewHolder)
            Snackbar.make(viewHolder.itemView, R.string.correct, Snackbar.LENGTH_LONG).show()
        }
        else {
            removeItem(viewHolder)
            restoreLastItem()
            Snackbar.make(viewHolder.itemView, R.string.incorrect, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getFalseOrTrueSwipe(direction: Int) : Boolean {
        if (direction == ItemTouchHelper.RIGHT)
            return true
        return false
    }

    private fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removedPosition = viewHolder.adapterPosition
        removedItem = questions[viewHolder.adapterPosition]

        questions.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    private fun restoreLastItem() {
        questions.add(removedPosition, removedItem)
        notifyItemInserted(removedPosition)
    }

    private fun onQuestionClick(viewHolder: RecyclerView.ViewHolder) {
        Snackbar.make(viewHolder.itemView,"Question: " +
                questions[viewHolder.adapterPosition].question + "\nAnswer: " +
                questions[viewHolder.adapterPosition].answer, Snackbar.LENGTH_LONG).show()
    }

    override  fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
        holder.itemView.setOnClickListener() {
            onQuestionClick(holder)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            itemView.tvQuestion.text = question.question
        }
    }

}