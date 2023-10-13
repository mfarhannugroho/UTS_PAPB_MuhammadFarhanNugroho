package com.example.percobaan2uts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        val asalSpinner: Spinner = findViewById(R.id.spinasal)
        val tujuanSpinner: Spinner = findViewById(R.id.spintujuan)
        val kelasSpinner: Spinner = findViewById(R.id.spinkelas)
        val hargaTextView: TextView = findViewById(R.id.harga)
        val toggleButtons = arrayOf(
            findViewById<ToggleButton>(R.id.toggleButton1),
            findViewById<ToggleButton>(R.id.toggleButton2),
            findViewById<ToggleButton>(R.id.toggleButton3),
            findViewById<ToggleButton>(R.id.toggleButton4),
            findViewById<ToggleButton>(R.id.toggleButton5),
            findViewById<ToggleButton>(R.id.toggleButton6),
            findViewById<ToggleButton>(R.id.toggleButton7),
            findViewById<ToggleButton>(R.id.toggleButton8)
        )

        // Inisialisasi Spinner untuk Asal, Tujuan, dan Kelas
        val asalArray = arrayOf("Stasiun Tugu Yogyakarta", "Stasiun Madiun", "Stasiun Surabaya Gubeng")
        val tujuanArray = arrayOf("Stasiun Tugu Yogyakarta", "Stasiun Madiun", "Stasiun Surabaya Gubeng")
        val kelasArray = arrayOf("Ekonomi", "Eksekutif")

        val asalAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, asalArray)
        val tujuanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tujuanArray)
        val kelasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kelasArray)

        asalSpinner.adapter = asalAdapter
        tujuanSpinner.adapter = tujuanAdapter
        kelasSpinner.adapter = kelasAdapter

        // Harga default
        var harga = 0

        // Menghitung harga berdasarkan pilihan Asal dan Tujuan
        fun calculateHarga() {
            val selectedAsal = asalSpinner.selectedItem.toString()
            val selectedTujuan = tujuanSpinner.selectedItem.toString()
            val selectedKelas = kelasSpinner.selectedItem.toString()

            // Logika perhitungan harga
            harga = when {
                (selectedAsal == "Stasiun Tugu Yogyakarta" && selectedTujuan == "Stasiun Madiun") ||
                        (selectedAsal == "Stasiun Madiun" && selectedTujuan == "Stasiun Tugu Yogyakarta") -> {
                    if (selectedKelas == "Ekonomi") 45000 else 65000
                }
                (selectedAsal == "Stasiun Madiun" && selectedTujuan == "Stasiun Surabaya Gubeng") ||
                        (selectedAsal == "Stasiun Surabaya Gubeng" && selectedTujuan == "Stasiun Madiun") -> {
                    if (selectedKelas == "Ekonomi") 45000 else 65000
                }
                (selectedAsal == "Stasiun Tugu Yogyakarta" && selectedTujuan == "Stasiun Surabaya Gubeng") ||
                        (selectedAsal == "Stasiun Surabaya Gubeng" && selectedTujuan == "Stasiun Tugu Yogyakarta") -> {
                    if (selectedKelas == "Ekonomi") 70000 else 100000
                }
                else -> 0
            }

            // Menghitung harga paket tambahan
            for (button in toggleButtons) {
                if (button.isChecked) {
                    harga += 10000
                }
            }

            hargaTextView.text = "Harga: Rp $harga"

            // Tambahkan pemberitahuan jika paket telah ditambahkan
            if (harga > 0) {
                Toast.makeText(this, "Pilihan telah ditambahkan!", Toast.LENGTH_SHORT).show()
            }
        }

        // Tambahkan Listener untuk Spinner
        asalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        tujuanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        kelasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Tambahkan Listener untuk ToggleButtons
        for (button in toggleButtons) {
            button.setOnCheckedChangeListener { _, _ -> calculateHarga() }
        }

        // Hitung harga awal saat aplikasi dibuka
        calculateHarga()

        // Tambahkan tombol "Dashboard" untuk pindah ke DashboardActivity
        val dashboardButton: Button = findViewById(R.id.buttoninput)
        dashboardButton.setOnClickListener {
            val datePicker = findViewById<DatePicker>(R.id.datetrip)
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth

            // Buat Intent untuk berpindah ke DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)

            // Kirim tanggal yang dipilih sebagai data tambahan
            intent.putExtra("tanggal", "$year-${month + 1}-$dayOfMonth")
            intent.putExtra("asal", asalSpinner.selectedItem.toString())
            intent.putExtra("tujuan", tujuanSpinner.selectedItem.toString())

            // Memeriksa toggle yang dipilih
            val selectedPaket = StringBuilder()
            for (button in toggleButtons) {
                if (button.isChecked) {
                    selectedPaket.append(button.text).append(", ") // Menambahkan paket yang dipilih
                }
            }

            // Hapus koma ekstra di akhir
            if (selectedPaket.isNotEmpty()) {
                selectedPaket.deleteCharAt(selectedPaket.length - 2)
            }

            intent.putExtra("paket", selectedPaket.toString())
            intent.putExtra("harga", harga)

            // Mulai aktivitas DashboardActivity
            startActivity(intent)
        }
    }
}
