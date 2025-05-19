package com.example.ss3_app_new

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ss3_app_new.databinding.ActivityAccountSettingsBinding

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "User")
        binding.profileName.text = userName

        //goes back to settings page
        binding.backArrow.setOnClickListener {
            finish()
        }

        // logs out and back to login screen
        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}