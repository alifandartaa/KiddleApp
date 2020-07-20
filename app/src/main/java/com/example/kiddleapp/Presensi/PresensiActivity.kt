package com.example.kiddleapp.Presensi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        supportActionBar?.hide()
    }
}