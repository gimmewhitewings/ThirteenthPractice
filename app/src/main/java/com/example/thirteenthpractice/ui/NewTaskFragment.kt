package com.example.thirteenthpractice.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.thirteenthpractice.data.TaskDataSource
import com.example.thirteenthpractice.data.DataBaseTaskDataSource
import com.example.thirteenthpractice.data.FileTaskDataSource
import com.example.thirteenthpractice.data.Task
import com.example.thirteenthpractice.databinding.FragmentNewTaskBinding

class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataSource: TaskDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        dataSource = if (sharedPreferences.getString("data_source", "data_base") == "data_base")
            DataBaseTaskDataSource(requireContext())
        else
            FileTaskDataSource(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            saveButton.setOnClickListener {
                val newTask = Task(
                    description = newTaskDescriptionEditText.text.toString(),
                    isDone = false
                )
                dataSource.addTask(newTask)
                findNavController().popBackStack()
            }
        }
    }

}