package com.example.ss3_app_new

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ss3_app_new.databinding.ActivityAddTaskBinding
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var selectedDate: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()

        binding.dateButton.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.timeInMillis
                    binding.dateButton.text = "$dayOfMonth/${month + 1}/$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.saveButton.setOnClickListener {
            try {
                val title = binding.titleInput.text.toString().trim()
                val description = binding.descriptionInput.text.toString().trim()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val task = Task(title, description, selectedDate)
                    TaskStorage.addTask(task)
                    Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.itemIconTintList = null

        binding.bottomNav.menu.findItem(R.id.nav_home).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_home)
        binding.bottomNav.menu.findItem(R.id.nav_add).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_add_selected)
        binding.bottomNav.menu.findItem(R.id.nav_settings).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_settings)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish()
                    true
                }
                else -> true
            }
        }
    }
}