package mbenedictt.paba.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var arrTask = ArrayList<task>()
    private lateinit var _rvTask : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _rvTask = findViewById<RecyclerView>(R.id.rvTask)

        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

        loadData()
        tampilkanData()
    }

    fun loadData(){
        arrTask.clear()
        for (i in dataTask.indices){
            arrTask.add(dataTask[i])
        }
    }

    fun tampilkanData(){
        _rvTask.layoutManager = LinearLayoutManager(this)

        val adapterTask = adapterRecView(arrTask)
        _rvTask.adapter = adapterTask

        adapterTask.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun delData(position: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("Delete "+dataTask[position].taskName+" Task?")
                    .setPositiveButton("Yes") { dialog, which ->
                        dataTask.removeAt(position)
                        loadData()
                        tampilkanData()
                    }
                    .setNegativeButton("No") { dialog, which ->
                        Toast.makeText(this@MainActivity, "Delete Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }

            override fun editData(position: Int) {
                val intent = Intent(this@MainActivity, AddTask::class.java)
                intent.putExtra("isEditMode", true)
                intent.putExtra("taskPosition", position)
                intent.putExtra("taskName", dataTask[position].taskName)
                intent.putExtra("taskDate", dataTask[position].taskDate)
                intent.putExtra("taskDescription", dataTask[position].taskDesc)
                startActivity(intent)
            }

            override fun changeStatus(position: Int) {
                if(dataTask[position].taskStatus == "Not Started"){
                    AlertDialog.Builder(this@MainActivity)
                        .setMessage("Start "+dataTask[position].taskName+" Task?")
                        .setPositiveButton("Yes") { dialog, which ->
                            dataTask[position].taskStatus = "Started"
                            loadData()
                            tampilkanData()
                        }
                        .setNegativeButton("No") { dialog, which ->
                            Toast.makeText(this@MainActivity, "Starting Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                        .show()
                } else if(dataTask[position].taskStatus == "Started"){
                    AlertDialog.Builder(this@MainActivity)
                        .setMessage("End "+dataTask[position].taskName+" Task?")
                        .setPositiveButton("Yes") { dialog, which ->
                            dataTask[position].taskStatus = "Finished"
                            loadData()
                            tampilkanData()
                        }
                        .setNegativeButton("No") { dialog, which ->
                            Toast.makeText(this@MainActivity, "End Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                        .show()
                }
            }
        })
    }

    companion object {
        val dataTask : MutableList<task> = mutableListOf()
    }
}