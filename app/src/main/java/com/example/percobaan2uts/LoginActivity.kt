package com.example.percobaan2uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.editTextTextPersonName)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        loginButton = findViewById(R.id.button1)

        sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Dapatkan data pengguna yang telah mendaftar
            val registeredEmail = sharedPreferences.getString("email", null)
            val registeredPassword = sharedPreferences.getString("password", null)

            if (email == registeredEmail && password == registeredPassword) {
                showMessage("Login berhasil")
                // Arahkan ke halaman beranda atau aktivitas lain yang sesuai
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                showMessage("Login gagal. Email atau password salah.")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
