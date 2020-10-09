package com.example.kiddleapp.Pengumuman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Beranda.BerandaFragment
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Pengumuman.Adapter.PengumumanAdapter
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.TugasActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pengumuman.*
import java.util.*
import kotlin.collections.ArrayList

class PengumumanActivity : AppCompatActivity() {

    private val pengumuman: ArrayList<Pengumuman> = arrayListOf()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman)

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)

        //intent untuk kembali ke halaman sebelumnya
        img_back_pengumuman.setOnClickListener {
            val intent: Intent = Intent(this@PengumumanActivity, MainActivity::class.java)
            startActivity(intent)
        }
        if(sharedPreferences.getString("kelas","") == "admin"){
            btn_tambah_pengumuman.visibility= View.VISIBLE
            btn_tambah_pengumuman.setOnClickListener {
                val intent: Intent = Intent(this@PengumumanActivity, EditPengumumanActivity::class.java)
                    .putExtra("jenis", "TAMBAH_PENGUMUMAN")
                startActivity(intent)
                finish()
            }

        }

        showRecylerList(pengumuman)

    }

    private fun showRecylerList(list: ArrayList<Pengumuman>): PengumumanAdapter {
        val adapter = PengumumanAdapter(list) {

        }

        getPagePengumumanList {item: ArrayList<Pengumuman> ->
            pengumuman.addAll(item)
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Collections.reverse(list);
            rv_pengumuman.layoutManager = LinearLayoutManager(this)
            rv_pengumuman.adapter = adapter
        }

        return adapter
    }

    private fun getPagePengumumanList(callback: (item:ArrayList<Pengumuman>) -> Unit) {
        val listPengumuman: ArrayList<Pengumuman> = arrayListOf()
        db.collection("Pengumuman").addSnapshotListener { value, error ->
            if (error != null) return@addSnapshotListener
            if (value!!.isEmpty) {
                Toast.makeText(
                    this,
                    "Pengumuman Tidak Tersedia",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                for (document in value!!) {
                    listPengumuman.add(
                        Pengumuman(
                            document.id,
                            document.getString("judul"),
                            document.getString("isi"),
                            document.getString("tanggal"),
                            document.getString("gambar"),
                            document.getString("video")
                        )
                    )
                }
                callback.invoke(listPengumuman)
            }
        }
    }
}