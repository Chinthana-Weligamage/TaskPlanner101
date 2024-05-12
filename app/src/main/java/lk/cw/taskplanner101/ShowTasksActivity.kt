package lk.cw.taskplanner101
// ShowTasksActivity.kt

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ShowTasksActivity : AppCompatActivity() {

    private lateinit var listViewTasks: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tasks)

        listViewTasks = findViewById(R.id.listViewTasks)

        // Retrieve tasks from the database (you'll need to implement this)
        val tasks = retrieveTasksFromDatabase()

        // Display tasks in the ListView using custom adapter
        val adapter = TaskListAdapter(this, tasks)
        listViewTasks.adapter = adapter
    }

    private fun retrieveTasksFromDatabase(): List<TaskItem> {
        // Implement this method to retrieve tasks from the database
        // and return a list of TaskItem objects
        // For demonstration purposes, returning a dummy list
        return listOf(
            TaskItem("Task 1", "Description 1", "2024-05-15"),
            TaskItem("Task 2", "Description 2", "2024-05-16"),
            TaskItem("Task 3", "Description 3", "2024-05-17")
        )
    }
}

