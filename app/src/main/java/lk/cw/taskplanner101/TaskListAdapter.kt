package lk.cw.taskplanner101
// TaskListAdapter.kt

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import lk.cw.taskplanner101.R

class TaskListAdapter(
    private val context: Context,
    private val dbHelper: TaskDatabaseHelper,
    private val tasks: MutableList<TaskItem>
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
            // Handle update button click
            // You can pass the task ID or any other identifier to the update activity
            // For example: startActivity(Intent(context, UpdateTaskActivity::class.java).putExtra("taskId", task.id))
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

    private class ViewHolder(view: View) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val textViewDeadline: TextView = view.findViewById(R.id.textViewDeadline)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }
}

