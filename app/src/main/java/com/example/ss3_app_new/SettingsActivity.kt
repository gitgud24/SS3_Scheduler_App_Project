package com.example.ss3_app_new

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ss3_app_new.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //open account settings activity / page
        binding.accountSettingsButton.setOnClickListener {
            startActivity(Intent(this, AccountSettingsActivity::class.java))
        }

        // Feedback section >
        binding.submitFeedbackButton.setOnClickListener {
            val rating = binding.ratingBar.rating
            val comment = binding.feedbackInput.text.toString().trim()

            if (comment.isNotEmpty()) {
                val feedbackMsg = "Rating: $rating\nComment: $comment"
                Toast.makeText(this, "Feedback Submitted\n$feedbackMsg", Toast.LENGTH_LONG).show()
                binding.feedbackInput.text?.clear()
                binding.ratingBar.rating = 0f
            } else {
                Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show()
            }
        }

        // Language dropdown
        val languages = resources.getStringArray(R.array.language_selection_array)
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languageAdapter

        // Theme selection dropdown
        val themes = resources.getStringArray(R.array.theme_selection_array)
        val themeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.themeSpinner.adapter = themeAdapter

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.itemIconTintList = null

        binding.bottomNav.menu.findItem(R.id.nav_home).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_home)
        binding.bottomNav.menu.findItem(R.id.nav_add).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_add)
        binding.bottomNav.menu.findItem(R.id.nav_settings).icon =
            ContextCompat.getDrawable(this, R.drawable.ic_settings_selected)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_add -> {
                    startActivity(Intent(this, AddTaskActivity::class.java))
                    finish()
                    true
                }
                else -> true
            }
        }
    }
}