package com.example.ss3_app_new

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ss3_app_new.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var weekCalendarAdapter: WeekCalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up - task list
        taskAdapter = TaskAdapter(TaskStorage.getTasksSortedByDate().take(5))
        binding.taskRecycler.layoutManager = LinearLayoutManager(this)
        binding.taskRecycler.adapter = taskAdapter

        // Set up - week calendar with month/year header
        val (monthYear, weekDays) = getCurrentWeekWithHeader()
        weekCalendarAdapter = WeekCalendarAdapter(monthYear, weekDays)
        binding.weekCalendarRecycler.layoutManager = LinearLayoutManager(this)
        binding.weekCalendarRecycler.adapter = weekCalendarAdapter

        binding.weekCalendarRecycler.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.itemIconTintList = null

        // Set icons for currently active activity - from the bottom nav bar
        binding.bottomNav.menu.findItem(R.id.nav_home).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_home_selected)
        binding.bottomNav.menu.findItem(R.id.nav_add).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_add)
        binding.bottomNav.menu.findItem(R.id.nav_settings).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_settings)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add -> {
                    startActivity(Intent(this, AddTaskActivity::class.java))
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

    private fun getCurrentWeekWithHeader(): Pair<String, List<Int>> {
        val calendar = Calendar.getInstance()
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val monthYear = monthYearFormat.format(calendar.time)

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        val days = mutableListOf<Int>()
        for (i in 0 until 7) {
            days.add(calendar.get(Calendar.DAY_OF_MONTH))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return Pair(monthYear, days)
    }

    override fun onResume() {
        super.onResume()
        taskAdapter.updateTasks(TaskStorage.getTasksSortedByDate().take(5))
    }
}