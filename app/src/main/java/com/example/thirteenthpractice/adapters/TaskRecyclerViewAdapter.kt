package com.example.thirteenthpractice.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thirteenthpractice.data.Task
import com.example.thirteenthpractice.databinding.FragmentTaskBinding

class TaskRecyclerViewAdapter(
    private val values: List<Task>,
    private val onTaskChecked: (Task) -> Unit
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewHolder = ViewHolder(
            FragmentTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.binding.apply {
            isDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    taskDescriptionTextView.paintFlags =
                        taskDescriptionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else
                    taskDescriptionTextView.paintFlags =
                        taskDescriptionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                val position = viewHolder.bindingAdapterPosition
                values[position].isDone = isChecked
                onTaskChecked(values[position])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(var binding: FragmentTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskDescriptionTextView.text = task.description
            binding.isDoneCheckBox.isChecked = task.isDone
        }
    }

}