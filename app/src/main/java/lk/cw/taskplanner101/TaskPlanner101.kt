package lk.cw.taskplanner101
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TaskPlanner101 : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDeadline: EditText
    private lateinit var buttonAddTask: Button
    private lateinit var buttonShowTasks: Button
    private lateinit var buttonDatePicker: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taskplanner101)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextDeadline = findViewById(R.id.editTextDeadline)
        buttonAddTask = findViewById(R.id.buttonAddTask)
        buttonShowTasks = findViewById(R.id.buttonShowTasks)
        buttonDatePicker = findViewById(R.id.buttonDatePicker)

        buttonAddTask.setOnClickListener {
            // Handle Add Task button click
            // ...
        }

        // Add click listener to navigate to ShowTasksActivity
        buttonShowTasks.setOnClickListener {
            val intent = Intent(this, ShowTasksActivity::class.java)
            startActivity(intent)
        }

        // Set up click listener for the date picker button
        buttonDatePicker.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Update the EditText field with the selected date
                val selectedDate = "${year}-${month + 1}-${dayOfMonth}"
                editTextDeadline.setText(selectedDate)
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }
}
