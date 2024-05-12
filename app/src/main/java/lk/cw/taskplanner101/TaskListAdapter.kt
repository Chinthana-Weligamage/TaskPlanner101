package lk.cw.taskplanner101

// TaskListAdapter.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class TaskListAdapter(private val context: Context, private val tasks: List<TaskItem>) : BaseAdapter() {

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
            // Handle delete button click
            // You can show a confirmation dialog before deleting the task
            // For example: showDeleteConfirmationDialog(task.id)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val textViewDeadline: TextView = view.findViewById(R.id.textViewDeadline)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }
}
