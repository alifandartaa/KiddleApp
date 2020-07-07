package com.example.kiddleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Notifikasi
import com.example.kiddleapp.model.Model_Notifikasi
import kotlinx.android.synthetic.main.activity_notifikasi.*

class Notifikasi : AppCompatActivity() {

    private var notifikasi = ArrayList<Model_Notifikasi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        rv_notifikasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        notifikasi.clear()

        val notif = Model_Notifikasi("Pembelajaran Jarak Jauh", R.drawable.notif, "7 Juli 2020, 10.00 WIB", "Penguman")
        notifikasi.add(notif)

        rv_notifikasi.adapter = Adapter_Notifikasi(notifikasi){

        }

    }
}