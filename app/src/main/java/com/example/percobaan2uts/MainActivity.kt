package com.example.percobaan2uts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var tglLahirEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var daftarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.editTextEmail)
        usernameEditText = findViewById(R.id.editTextUsername)
        tglLahirEditText = findViewById(R.id.editTexttgllahir)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        daftarButton = findViewById(R.id.btnReg)

        daftarButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val username = usernameEditText.text.toString()
            val tglLahir = tglLahirEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || username.isEmpty() || tglLahir.isEmpty() || password.isEmpty()) {
                showMessage("Semua kolom harus diisi")
                return@setOnClickListener
            }

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val today = Date()
            val tglLahirDate = dateFormat.parse(tglLahir)

            if (tglLahirDate == null) {
                showMessage("Format tanggal lahir tidak valid")
                return@setOnClickListener
            }

            // Hitung usia
            val age = calculateAge(tglLahirDate, today)

            if (age < 15) {
                showMessage("Anda harus berusia 15 tahun atau lebih")
            } else {
                showMessage("Pendaftaran berhasil")

                val sharedPrefsEditor = getSharedPreferences("user_preferences", Context.MODE_PRIVATE).edit()
                sharedPrefsEditor.putString("email", email)
                sharedPrefsEditor.putString("password", password)
                sharedPrefsEditor.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun calculateAge(birthDate: Date, currentDate: Date): Int {
        val birthYear = SimpleDateFormat("yyyy").format(birthDate).toInt()
        val currentYear = SimpleDateFormat("yyyy").format(currentDate).toInt()
        return currentYear - birthYear
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
