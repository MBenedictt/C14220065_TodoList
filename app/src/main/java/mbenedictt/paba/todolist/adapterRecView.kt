package mbenedictt.paba.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterRecView (private val listTask: ArrayList<task>) : RecyclerView.Adapter<adapterRecView.ListViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(position: Int)

        fun editData(position: Int)

        fun changeStatus(position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTask = itemView.findViewById<TextView>(R.id.tvTask)
        var _tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        var _tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
        var _tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

        var _btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
        var _btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
        var _btnStatus = itemView.findViewById<Button>(R.id.btnStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var task = listTask[position]
        holder._tvTask.setText(task.taskName)
        holder._tvDate.setText(task.taskDate)
        holder._tvDesc.setText(task.taskDesc)
        holder._tvStatus.setText(task.taskStatus)

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(position)
        }

        holder._btnStatus.setOnClickListener {
            onItemClickCallback.changeStatus(position)
        }

        holder._btnEdit.setOnClickListener {
            onItemClickCallback.editData(position)
        }

        if(task.taskStatus != "Not Started"){
            holder._btnEdit.visibility = View.INVISIBLE
        } else {
            holder._btnEdit.visibility = View.VISIBLE
        }

        if(task.taskStatus == "Not Started"){
            holder._btnStatus.text = "Start"
        } else if (task.taskStatus == "Started"){
            holder._btnStatus.text = "End"
        }

        if(task.taskStatus == "Finished"){
            holder._btnStatus.visibility = View.INVISIBLE
        } else {
            holder._btnStatus.visibility = View.VISIBLE
        }
    }
}