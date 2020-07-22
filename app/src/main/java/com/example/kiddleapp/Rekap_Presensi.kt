package com.example.kiddleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Notifikasi
import com.example.kiddleapp.adapter.Adapter_Rekap_Presensi
import com.example.kiddleapp.model.Model_Notifikasi
import com.example.kiddleapp.model.Model_Rekap_Presensi
import kotlinx.android.synthetic.main.activity_notifikasi.*
import kotlinx.android.synthetic.main.activity_rekap__presensi.*

class Rekap_Presensi : AppCompatActivity() {

    //untuk menyimpan notifikasi
    private var rekap = ArrayList<Model_Rekap_Presensi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap__presensi)

        //recyclerView dengan linear layout
        rv_rekap_presensi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        rekap.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Rekap_Presensi("Juli 2020", "Hadir: 20", "Sakit: 2", "Izin: 1", "Alpha: 0")
        rekap.add(temp)

        //agar notifikasi dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_rekap_presensi.adapter = Adapter_Rekap_Presensi(rekap){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_rekap.setOnClickListener {
            onBackPressed()
        }
    }
}