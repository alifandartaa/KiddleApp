package com.example.kiddleapp.Presensi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_presensi.*

class PresensiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presensi)

        btn_selanjutnya_presensi.setOnClickListener {
            val intent = Intent(this@PresensiActivity, DetailPresensiActivity::class.java)
            startActivity(intent)
        }

        ic_back_presensi.setOnClickListener {
            startActivity(Intent(this@PresensiActivity, MainActivity::class.java))
        }
        //adapter untuk dropdown presensi
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_kelas_presensi.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        supportActionBar?.hide()
    }
}