package mbenedictt.paba.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate

class AddTask : AppCompatActivity() {

    private var isEditMode = false
    private var taskPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val _tvJudul = findViewById<TextView>(R.id.tvJudul)
        val _etNamaTask = findViewById<EditText>(R.id.namaTask)
        val _etDeskripsiTask = findViewById<EditText>(R.id.deskripsiTask)
        val _StartDate = LocalDate.now()
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)

        val intent = intent
        isEditMode = intent.getBooleanExtra("isEditMode", false)
        taskPosition = intent.getIntExtra("taskPosition", -1)

        if (isEditMode) {
            taskPosition = intent.getIntExtra("taskPosition", -1)
            val taskName = intent.getStringExtra("taskName")
            val taskDescription = intent.getStringExtra("taskDescription")

            _etNamaTask.setText(taskName)
            _etDeskripsiTask.setText(taskDescription)

            _tvJudul.text = "Edit Task"
            btnAddTask.text = "Edit Task"
        }

        btnAddTask.setOnClickListener {
            val nama = _etNamaTask.text.toString()
            val tanggal = _StartDate.toString()
            val deskripsi = _etDeskripsiTask.text.toString()

            if (nama.isNotEmpty() && tanggal.isNotEmpty() && deskripsi.isNotEmpty()) {
                if (isEditMode) {
                    MainActivity.dataTask[taskPosition].taskName = nama
                    MainActivity.dataTask[taskPosition].taskDate = tanggal
                    MainActivity.dataTask[taskPosition].taskDesc = deskripsi
                } else {
                    var task = task(nama, "Not Started", tanggal, deskripsi)
                    MainActivity.dataTask.add(task)
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}