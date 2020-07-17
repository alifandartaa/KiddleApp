package com.example.kiddleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Murid
import com.example.kiddleapp.adapter.Adapter_Tugas
import com.example.kiddleapp.model.Model_Murid
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.activity_rapor.*
import kotlinx.android.synthetic.main.activity_rapor.img_back_rapor
import kotlinx.android.synthetic.main.activity_tugas.*

class Tugas : AppCompatActivity() {

    //untuk menyimpan Tugas
    private var tugas = ArrayList<Model_Tugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tugas)

        //untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_tugas_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        rv_tugas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        tugas.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Tugas( "Bintang A", "Keterampilan", "Kerjakan Soal pada akhir video", "20 Juli 2020", "20.20 WIB", "20/20",0,R.raw.video_tugas,"https://youtu.be/g9aXIpJFKyU")
        tugas.add(temp)

        val temp2 = Model_Tugas( "Bintang A", "Keterampilan", "Kerjakan Soal pada akhir video", "20 Juli 2020", "20.20 WIB", "20/20",R.drawable.banner_tugas,0,"https://youtu.be/g9aXIpJFKyU")
        tugas.add(temp2)
        val temp3 = Model_Tugas("Bintang A", "Keterampilan", "Kerjakan Soal pada akhir video", "20 Juli 2020", "20.20 WIB", "20/20",0,R.raw.video_tugas,"")
        tugas.add(temp3)

        val temp4 = Model_Tugas( "Bintang A", "Keterampilan", "Kerjakan Soal pada akhir video", "20 Juli 2020", "20.20 WIB", "20/20",R.drawable.banner_tugas,0,"https://youtu.be/g9aXIpJFKyU")
        tugas.add(temp4)
        val temp5 = Model_Tugas( "Bintang A", "Keterampilan", "Kerjakan Soal pada akhir video", "20 Juli 2020", "20.20 WIB", "20/20",0,R.raw.video_tugas,"")
        tugas.add(temp5)



        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_tugas.adapter = Adapter_Tugas(tugas){
            val intent: Intent = Intent(this@Tugas, Detail_Tugas::class.java).putExtra("data", it)
            startActivity(intent)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_tugas.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_tugas.setOnClickListener {
            val intent: Intent = Intent(this@Tugas, Edit_Tugas::class.java).putExtra("jenis", "TAMBAH_TUGAS")
            startActivity(intent)
        }

    }
}