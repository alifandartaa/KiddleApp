package com.example.kiddleapp.Jurnal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Beranda.BerandaFragment
import com.example.kiddleapp.Jurnal.Adapter.JurnalAdapter
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Adapter.TugasAdapter
import com.example.kiddleapp.Tugas.Model.Tugas
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_jurnal.*
import kotlinx.android.synthetic.main.activity_tugas.*

class JurnalActivity : AppCompatActivity() {

    //untuk menyimpan JurnalActivity

    private val jurnal: ArrayList<Jurnal> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val jurnalCollection = db.collection("Jurnal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jurnal)

        showRecyclerList(jurnal)

        //intent untuk kembali ke halaman sebelumnya
        img_back_jurnal.setOnClickListener {
            val intent: Intent =
                Intent(this@JurnalActivity,MainActivity::class.java)
            startActivity(intent)
        }

        btn_tambah_jurnal.setOnClickListener {
            val intent: Intent =
                Intent(this@JurnalActivity, EditJurnalActivity::class.java).putExtra(
                    "jenis",
                    "TAMBAH_JURNAL"
                )
            startActivity(intent)
        }

        //untuk dropdown
        val kelas = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar","Semua Kelas")
        val adapter_kelas = ArrayAdapter(this, R.layout.dropdown_text, kelas)
        (dropdown_jurnal_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter_kelas)

        auto_kelas_jurnal.setOnItemClickListener { parent, view, position, id ->
            var item = parent.getItemAtPosition(position).toString()
            jurnal.clear()
            showRecyclerList(jurnal)
        }


    }
    //recyclerView Jurnal
    private fun showRecyclerList(list: ArrayList<Jurnal>): JurnalAdapter {
        val adapter = JurnalAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")
        }

        getPageTugasList { item: ArrayList<Jurnal> ->
            jurnal.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_jurnal.layoutManager = LinearLayoutManager(this)
            rv_jurnal.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Jurnal>) -> Unit) {
        val listJurnal: ArrayList<Jurnal> = arrayListOf()
        if(auto_kelas_jurnal.text.toString()=="Semua Kelas"|| auto_kelas_jurnal.text.toString()==""){
            jurnalCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    Log.d("TugasActivity", document.toString())
                    listJurnal.add(
                        Jurnal(
                            document.id,
                            document.getString("jenis"),
                            document.getString("kelas"),
                            document.getString("judul"),
                            document.getString("isi"),
                            document.getString("tanggal"),
                            document.getString("gambar"),
                            document.getString("video")

                        )
                    )

                }

                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listJurnal)
            }
        }else if(auto_kelas_jurnal.text.toString()!="Semua Kelas"){
            jurnalCollection.whereEqualTo("kelas",auto_kelas_jurnal.text.toString()).addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    Log.d("TugasActivity", document.toString())
                    listJurnal.add(
                        Jurnal(
                            document.id,
                            document.getString("jenis"),
                            document.getString("kelas"),
                            document.getString("judul"),
                            document.getString("isi"),
                            document.getString("tanggal"),
                            document.getString("gambar"),
                            document.getString("video")

                        )
                    )

                }

                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listJurnal)
            }


        }

        Log.d("Tugas Activity", "getPageTugasList: after get collection data, should not be printed")
    }
}