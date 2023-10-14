package com.example.percobaan2uts

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class DashboardActivity : AppCompatActivity() {
    private var selectedDate: String? = null // Tambahkan variabel untuk tanggal terpilih

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tanggalTextView = findViewById<TextView>(R.id.tgldash2)
        val asalTextView = findViewById<TextView>(R.id.asaldash2)
        val tujuanTextView = findViewById<TextView>(R.id.tujuandash2)
        val paketTextView = findViewById<TextView>(R.id.paketdash2)
        val hargaTextView = findViewById<TextView>(R.id.hargadash2)

        // Retrieve data from Intent
        val intent = intent
        if (intent != null) {
            selectedDate = intent.getStringExtra("tanggal") // Ambil tanggal dari Intent
            val asal = intent.getStringExtra("asal")
            val tujuan = intent.getStringExtra("tujuan")
            val paket = intent.getStringExtra("paket")
            val harga = intent.getIntExtra("harga", 0) // Default value if not found

            tanggalTextView.text = selectedDate // Tampilkan tanggal yang dipilih
            asalTextView.text = asal
            tujuanTextView.text = tujuan
            paketTextView.text = paket
            hargaTextView.text = "Harga: Rp $harga"
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Check if the selectedDate matches the date clicked on the calendar
            if (selectedDate == "$year-${month + 1}-$dayOfMonth") {
                Toast.makeText(
                    this,
                    "Ada Rencana Perjalan Untuk $year-${month + 1}-$dayOfMonth",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Tidak Ada Perjalanan Untuk $year-${month + 1}-$dayOfMonth",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Setelah kode yang sudah ada
        val rencanaButton = findViewById<AppCompatButton>(R.id.button2)
        rencanaButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi tombol "Logout"
        val logoutButton = findViewById<AppCompatButton>(R.id.button3)
        logoutButton.setOnClickListener {
            // Buat Intent untuk pindah ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // Tambahkan flag untuk membersihkan aktivitas sebelumnya
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Mulai LoginActivity dan hapus semua aktivitas sebelumnya
            startActivity(intent)

        }
    }
}
