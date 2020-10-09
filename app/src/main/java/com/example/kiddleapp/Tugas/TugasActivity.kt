package com.example.kiddleapp.Tugas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Adapter.HasilTugasAdapter
import com.example.kiddleapp.Tugas.Adapter.TugasAdapter
import com.example.kiddleapp.Tugas.Model.Tugas
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_jurnal.*
import kotlinx.android.synthetic.main.activity_tugas.*
import java.security.AccessController.getContext
import java.util.*
import kotlin.collections.ArrayList

class TugasActivity : AppCompatActivity() {

    private val tugas: ArrayList<Tugas> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val tugasCollection = db.collection("Tugas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tugas)
        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)

        if(sharedPreferences.getString("kelas","") == "admin"){
            tugas.clear()
            showRecyclerList(tugas)
            //untuk dropdown
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar", "Semua Kelas")
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_tugas_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            auto_kelas_tugas.setOnItemClickListener { parent, view, position, id ->
                var item = parent.getItemAtPosition(position).toString()
                tugas.clear()
                showRecyclerList(tugas)
            }
        }else{
            dropdown_tugas_kelas.isEnabled=true
            auto_kelas_tugas.setText(sharedPreferences.getString("kelas",""))
            tugas.clear()
            showRecyclerList(tugas)
        }


        //intent untuk kembali ke halaman sebelumnya
        img_back_tugas.setOnClickListener {
            val intent: Intent = Intent(this@TugasActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_tambah_tugas.setOnClickListener {
            val intent: Intent = Intent(this@TugasActivity, EditTugasActivity::class.java).putExtra(
                "jenis",
                "TAMBAH_TUGAS"
            )
            startActivity(intent)
            finish()
        }

    }

    private fun showRecyclerList(list: ArrayList<Tugas>): TugasAdapter {
        val adapter = TugasAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")

        }

        getPageTugasList { item: ArrayList<Tugas> ->
            tugas.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Collections.reverse(list);
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_tugas.layoutManager = LinearLayoutManager(this)
            rv_tugas.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Tugas>) -> Unit) {

        val listTugas: ArrayList<Tugas> = arrayListOf()
        if (auto_kelas_tugas.text.toString() == "Semua Kelas" || auto_kelas_tugas.text.toString() == "") {
            tugasCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(result!!.isEmpty){
                    Toast.makeText(
                        this,
                        "Tugas Tidak Tersedia",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }else{
                    for (document in result!!) {
                        Log.d("TugasActivity", document.toString())
                        listTugas.add(
                            Tugas(
                                document.id,
                                document.getString("kelas"),
                                document.getString("judul"),
                                document.getString("isi"),
                                document.getString("tanggal"),
                                document.getString("jam"),
                                document.getString("gambar"),
                                document.getString("video"),
                                document.getString("link")
                            )
                        )

                    }
                }

                callback.invoke(listTugas)
            }

        } else if (auto_kelas_tugas.text.toString() != "Semua Kelas") {
            tugasCollection.whereEqualTo("kelas", auto_kelas_tugas.text.toString())
                .addSnapshotListener { result, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (result!!.isEmpty) {
                        Toast.makeText(
                            this,
                            "Tugas Tidak Tersedia",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        for (document in result!!) {
                            Log.d("TugasActivity", document.toString())
                            listTugas.add(
                                Tugas(
                                    document.id,
                                    document.getString("kelas"),
                                    document.getString("judul"),
                                    document.getString("isi"),
                                    document.getString("tanggal"),
                                    document.getString("jam"),
                                    document.getString("gambar"),
                                    document.getString("video"),
                                    document.getString("link")
                                )
                            )

                        }
                        callback.invoke(listTugas)
                    }
                }
        }
    }
}





