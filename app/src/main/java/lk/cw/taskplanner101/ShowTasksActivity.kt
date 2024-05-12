package lk.cw.taskplanner101
// ShowTasksActivity.kt

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShowTasksActivity : AppCompatActivity() {

    private lateinit var listViewTasks: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tasks)

        listViewTasks = findViewById(R.id.listViewTasks)

        // Retrieve tasks from the database (you'll need to implement this)
        val tasks = retrieveTasksFromDatabase()

        // Display tasks in the ListView using custom adapter
        val dbHelper = TaskDatabaseHelper(this)
        val adapter = TaskListAdapter(this, dbHelper, tasks.toMutableList())
        listViewTasks.adapter = adapter


        val fabAddTask: FloatingActionButton = findViewById(R.id.fabAddTask)
        fabAddTask.setOnClickListener {
            // Start the AddTaskActivity when the FAB is clicked
            val intent = Intent(this, TaskPlanner101::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("Range")
    private fun retrieveTasksFromDatabase(): List<TaskItem> {
        val dbHelper = TaskDatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val tasks = mutableListOf<TaskItem>()

        val cursor = db.query(
            TaskDatabaseHelper.TABLE_NAME,
            arrayOf(
                TaskDatabaseHelper.COLUMN_ID, // Include ID in the projection
                TaskDatabaseHelper.COLUMN_TITLE,
                TaskDatabaseHelper.COLUMN_DESCRIPTION,
                TaskDatabaseHelper.COLUMN_DEADLINE
            ),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_DESCRIPTION))
            val deadline = cursor.getString(cursor.getColumnIndex(TaskDatabaseHelper.COLUMN_DEADLINE))
            tasks.add(TaskItem(id, title, description, deadline)) // Pass ID to TaskItem constructor
        }

        cursor.close()
        db.close()

        return tasks
    }
}

