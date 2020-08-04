package com.example.kiddleapp.RekapPresensi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.R
import com.example.kiddleapp.RekapPresensi.Adapter.RekapPresensiAdapter
import com.example.kiddleapp.RekapPresensi.Model.RekapPresensi
import kotlinx.android.synthetic.main.activity_rekap__presensi.*

class RekapPresensiActivity : AppCompatActivity() {

    //untuk menyimpan notifikasi
    private var rekap = ArrayList<RekapPresensi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap__presensi)

        //recyclerView dengan linear layout
        rv_rekap_presensi.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        rekap.clear()

        //bisa diganti dengan data dari firebase
        val temp =
            RekapPresensi(
                "Juli 2020",
                "Hadir: 20",
                "Sakit: 2",
                "Izin: 1",
                "Alpha: 0"
            )
        rekap.add(temp)

        //agar notifikasi dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_rekap_presensi.adapter =
            RekapPresensiAdapter(
                rekap
            ) {
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            }

        //intent untuk kembali ke halaman sebelumnya
        img_back_rekap.setOnClickListener {
            onBackPressed()
        }
    }
}