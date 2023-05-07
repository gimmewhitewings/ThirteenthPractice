package com.example.thirteenthpractice.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thirteenthpractice.R
import com.example.thirteenthpractice.data.TaskDataSource
import com.example.thirteenthpractice.adapters.TaskRecyclerViewAdapter
import com.example.thirteenthpractice.data.DataBaseTaskDataSource
import com.example.thirteenthpractice.data.FileTaskDataSource
import com.example.thirteenthpractice.databinding.FragmentTaskListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A fragment representing a list of Items.
 */
class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataSource: TaskDataSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        dataSource = if (sharedPreferences.getString("data_source", "data_base") == "data_base")
            DataBaseTaskDataSource(requireContext())
        else
            FileTaskDataSource(requireContext())

        val isUsernameVisible = sharedPreferences.getBoolean("show_username", false)
        val usernameVisibility =
            if (isUsernameVisible) View.VISIBLE else View.GONE
        binding.userNameTextView.visibility = usernameVisibility
        if (isUsernameVisible) {
            binding.userNameTextView.text = sharedPreferences.getString("username", "")
        }

        val isDateVisible = sharedPreferences.getBoolean("show_date", false)
        binding.dateTextView.apply {
            visibility = if (isDateVisible) View.VISIBLE else View.GONE
            if (isDateVisible) {
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            }
        }

        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TaskRecyclerViewAdapter(dataSource.getTasks()) { task ->
                dataSource.updateTask(task)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_newTaskFragment)
        }
    }
}