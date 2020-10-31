package com.example.kiddleapp.Kegiatan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.View
import android.widget.Toast
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
import java.util.*
import kotlin.collections.ArrayList

class KegiatanActivity : AppCompatActivity() {

    private val kegiatan: ArrayList<Kegiatan> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val kegiatanCollection = db.collection("Kegiatan")
    private val parentingCollection = db.collection("Parenting")
    private val materiCollection = db.collection("Materi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kegiatan)

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)

        kegiatan.clear()
        showRecyclerList(kegiatan)


        if(sharedPreferences.getString("kelas","") == "admin"){
            btn_tambah_jenis_fungsi.visibility=View.VISIBLE

            btn_tambah_jenis_fungsi.setOnClickListener {
                if (intent.getStringExtra("jenis") == "Kegiatan") {
                    val intent: Intent = Intent(
                        this@KegiatanActivity,
                        EditKegiatanActivity::class.java
                    ).putExtra("jenis", "TAMBAH_KEGIATAN")
                    startActivity(intent)
                    finish()
                } else if (intent.getStringExtra("jenis") == "Parenting") {
                    val intent: Intent = Intent(
                        this@KegiatanActivity,
                        EditKegiatanActivity::class.java
                    ).putExtra("jenis", "TAMBAH_PARENTING")
                    startActivity(intent)
                    finish()
                } else if (intent.getStringExtra("jenis") == "Materi") {
                    val intent: Intent = Intent(
                        this@KegiatanActivity,
                        EditKegiatanActivity::class.java
                    ).putExtra("jenis", "TAMBAH_MATERI")
                    startActivity(intent)
                    finish()
                }

            }

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_kegiatan.setOnClickListener {
            if (intent.getStringExtra("jenis") == "KEGIATAN") {
                onBackPressed()
            }else{
                val intent: Intent =
                    Intent(this@KegiatanActivity, MainActivity::class.java)
                startActivity(intent)
            }


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
            Collections.reverse(list);
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_jenis_fungsi.layoutManager = LinearLayoutManager(this)
            rv_jenis_fungsi.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Kegiatan>) -> Unit) {
        val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
        if (intent.getStringExtra("jenis") == "Kegiatan") {
            tv_jenis_fungsi.setText(R.string.kegiatan)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_kegiatan)


            kegiatanCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (result!!.isEmpty) {
                    Toast.makeText(
                        this,
                        "Kegiatan Tidak Tersedia",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
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

        } else if (intent.getStringExtra("jenis") == "Parenting") {
            tv_jenis_fungsi.setText(R.string.parenting)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_parenting)
            val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
            parentingCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(result!!.isEmpty){
                    Toast.makeText(
                        this,
                        "Parenting Tidak Tersedia",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }else {
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
                }
                callback.invoke(listKegiatan)
            }

        } else if (intent.getStringExtra("jenis") == "Materi") {
            tv_jenis_fungsi.setText(R.string.materi)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_materi)
            val listKegiatan: ArrayList<Kegiatan> = arrayListOf()
            materiCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (result!!.isEmpty) {
                Toast.makeText(
                    this,
                    "Materi Anak Tidak Tersedia",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
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
}