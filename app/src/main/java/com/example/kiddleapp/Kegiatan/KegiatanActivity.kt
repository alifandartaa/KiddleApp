package com.example.kiddleapp.Kegiatan

import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Beranda.BerandaFragment
import com.example.kiddleapp.Jurnal.Adapter.JurnalAdapter
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.Kegiatan.Adapter.Adapter_Kegiatan

import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_jurnal.*

import kotlinx.android.synthetic.main.activity_kegiatan.*

class KegiatanActivity : AppCompatActivity() {

    private val kegiatan: ArrayList<Kegiatan> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val kegiatanCollection = db.collection("Kegiatan")
    private val parentingCollection = db.collection("Parenting")
    private val materiCollection = db.collection("Materi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kegiatan)
       kegiatan.clear()
        showRecyclerList(kegiatan)

        btn_tambah_jenis_fungsi.setOnClickListener {
            if (intent.getStringExtra("jenis") == "KEGIATAN") {
                val intent: Intent = Intent(
                    this@KegiatanActivity,
                    EditKegiatanActivity::class.java
                ).putExtra("jenis", "TAMBAH_KEGIATAN")
                startActivity(intent)
            } else if (intent.getStringExtra("jenis") == "PARENTING") {
                val intent: Intent = Intent(
                    this@KegiatanActivity,
                    EditKegiatanActivity::class.java
                ).putExtra("jenis", "TAMBAH_PARENTING")
                startActivity(intent)
            } else if (intent.getStringExtra("jenis") == "MATERI") {
                val intent: Intent = Intent(
                    this@KegiatanActivity,
                    EditKegiatanActivity::class.java
                ).putExtra("jenis", "TAMBAH_MATERI")
                startActivity(intent)
            }

        }
        //intent untuk kembali ke halaman sebelumnya
        img_back_kegiatan.setOnClickListener {
            val intent: Intent =
                Intent(this@KegiatanActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showRecyclerList(list: ArrayList<Kegiatan>): Adapter_Kegiatan {
        val adapter = Adapter_Kegiatan(list) {
            //Log.d("Tugas Activity", "Result: $it")
        }

        getPageTugasList { item: ArrayList<Kegiatan> ->
            kegiatan.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_jenis_fungsi.layoutManager = LinearLayoutManager(this)
            rv_jenis_fungsi.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Kegiatan>) -> Unit) {
        val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
        if (intent.getStringExtra("jenis") == "KEGIATAN") {
            tv_jenis_fungsi.setText(R.string.kegiatan)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_kegiatan)

            val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
            kegiatanCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    listKegiatan.add(
                        Kegiatan(
                            document.id,
                            document.getString("jenis"),
                            document.getString("judul"),
                            document.getString("isi"),
                            document.getString("tanggal"),
                            document.getString("gambar"),
                            document.getString("video"),
                            document.getString("link")
                        )
                    )

                }
                callback.invoke(listKegiatan)
            }

        } else if (intent.getStringExtra("jenis") == "PARENTING") {
            tv_jenis_fungsi.setText(R.string.parenting)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_parenting)
            val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
            parentingCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    listKegiatan.add(
                        Kegiatan(
                            document.id,
                            document.getString("jenis"),
                            document.getString("judul"),
                            document.getString("isi"),
                            document.getString("tanggal"),
                            document.getString("gambar"),
                            document.getString("video"),
                            document.getString("link")
                        )
                    )

                }
                callback.invoke(listKegiatan)
            }

        } else if (intent.getStringExtra("jenis") == "MATERI") {
            tv_jenis_fungsi.setText(R.string.materi)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_materi)
            val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
            materiCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    listKegiatan.add(
                        Kegiatan(
                            document.id,
                            document.getString("jenis"),
                            document.getString("judul"),
                            document.getString("isi"),
                            document.getString("tanggal"),
                            document.getString("gambar"),
                            document.getString("video"),
                            document.getString("link")
                        )
                    )

                }
                callback.invoke(listKegiatan)
            }

        }
    }
}