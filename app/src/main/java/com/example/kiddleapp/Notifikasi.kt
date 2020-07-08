package com.example.kiddleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Notifikasi
import com.example.kiddleapp.model.Model_Notifikasi
import kotlinx.android.synthetic.main.activity_notifikasi.*

class Notifikasi : AppCompatActivity() {

    //untuk menyimpan notifikasi
    private var notifikasi = ArrayList<Model_Notifikasi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        //recyclerView dengan linear layout
        rv_notifikasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //mengkosongkan isi arraylist
        notifikasi.clear()

        //bisa diganti dengan data dari firebase
        val notif = Model_Notifikasi("Pembelajaran Jarak Jauh", R.drawable.notif, "7 Juli 2020, 10.00 WIB", "Penguman")
        notifikasi.add(notif)

        //agar notifikasi dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_notifikasi.adapter = Adapter_Notifikasi(notifikasi){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_notifikasi.setOnClickListener {
            onBackPressed()
        }

    }
}