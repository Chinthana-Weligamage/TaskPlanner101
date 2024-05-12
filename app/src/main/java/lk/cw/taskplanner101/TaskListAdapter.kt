package lk.cw.taskplanner101
// TaskListAdapter.kt

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class TaskListAdapter(
    private val context: Context,
    private val dbHelper: TaskDatabaseHelper,
    private var tasks: MutableList<TaskItem>
) : BaseAdapter() {


    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val task = tasks[position]
        viewHolder.textViewTitle.text = task.title
        viewHolder.textViewDescription.text = task.description
        viewHolder.textViewDeadline.text = task.deadline

        viewHolder.buttonUpdate.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_description, null)
            val editTextDescription = dialogView.findViewById<EditText>(R.id.editTextDescription)
            editTextDescription.setText(task.description)

            val alertDialogBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Update Description")
                .setPositiveButton("Save") { dialog, _ ->
                    // Save updated description
                    val newDescription = editTextDescription.text.toString()
                    // Implement the logic to update the description in the database
                    // For demonstration, let's just update the task object
                    task.description = newDescription
                    notifyDataSetChanged() // Update the list view
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            alertDialogBuilder.show()
        }


        viewHolder.buttonDelete.setOnClickListener {
            showDeleteConfirmationDialog(task)
        }

        return view
    }



    private fun showDeleteConfirmationDialog(task: TaskItem) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirm Deletion")
        alertDialogBuilder.setMessage("Are you sure you want to delete this task?")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
            val deletedRows = dbHelper.deleteTask(task.id)
            if (deletedRows > 0) {
                // Task deleted successfully
                tasks.remove(task)
                notifyDataSetChanged()
                showToast("Task deleted successfully!")
            } else {
                // Failed to delete task
                showToast("Failed to delete task. Please try again.")
            }
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    // Function to update task description in the database
    private fun updateTaskDescriptionInDatabase(taskId: Long, newDescription: String): Int {
        return dbHelper.updateTaskDescription(taskId, newDescription)
    }

    // Example usage when updating task description
    private fun updateTaskDescription(taskId: Long, newDescription: String) {
        // Call the function to update the task description in the database
        val count = updateTaskDescriptionInDatabase(taskId, newDescription)
        if (count > 0) {
            showToast("Task description updated successfully!")
            // Optionally, you can refresh the UI or navigate back to the task list
        } else {
            showToast("Failed to update task description. Please try again.")
        }
    }



    private class ViewHolder(view: View) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val textViewDeadline: TextView = view.findViewById(R.id.textViewDeadline)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

