package lk.cw.taskplanner101
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TaskPlanner101 : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDeadline: EditText
    private lateinit var buttonAddTask: Button
    private lateinit var buttonShowTasks: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taskplanner101)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextDeadline = findViewById(R.id.editTextDeadline)
        buttonAddTask = findViewById(R.id.buttonAddTask)
        buttonShowTasks = findViewById(R.id.buttonShowTasks)

        buttonAddTask.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            val deadline = editTextDeadline.text.toString()

            // Add your logic to add the task to the database
            // For example:
            // addTaskToDatabase(title, description, deadline)

            Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show()
        }

        buttonShowTasks.setOnClickListener {
            // Add your logic to show tasks
            // For example:
            // showTasksFromDatabase()
        }
    }
}
