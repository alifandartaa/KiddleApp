package com.example.kiddleapp.Presensi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R

class DetailPresensiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.hide()
    }
}