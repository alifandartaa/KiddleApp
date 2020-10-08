package com.example.kiddleapp.Rapor

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
import com.example.kiddleapp.Murid.Adapter.MuridAdapter
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.example.kiddleapp.Rapor.Adapter.RaporAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_rapor.*
import kotlinx.android.synthetic.main.fragment_murid.view.*

class RaporActivity : AppCompatActivity() {

    //untuk menyimpan murid
    private val murid: ArrayList<Murid> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val muridCollection = db.collection("Murid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rapor)


        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)


        if(sharedPreferences.getString("kelas","") == "admin"){
            murid.clear()
            showRecyclerList(murid)

            //untuk dropdown. bisa ganti kelas yang ada di firebase nya untuk dropdown
            val kelas = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar", "Semua Kelas")
            val adapter_kelas = ArrayAdapter(this, R.layout.dropdown_text, kelas)
            (dropdown_rapor_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter_kelas)

            //mengkosongkan isi arraylist
            // murid.clear()
            dropdown_value_rapor_kelas.setOnItemClickListener { parent, view, position, id ->
                var item = parent.getItemAtPosition(position).toString()
                murid.clear()
                showRecyclerList(murid)
            }
        }else{
            dropdown_rapor_kelas.isEnabled=true
            dropdown_value_rapor_kelas.setText(sharedPreferences.getString("kelas",""))
            murid.clear()
            showRecyclerList(murid)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_rapor.setOnClickListener {
            val intent: Intent = Intent(this@RaporActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun showRecyclerList(list: ArrayList<Murid>): RaporAdapter {
        murid.clear()
        val adapter = RaporAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")
        }

        getPageTugasList { item: ArrayList<Murid> ->
            murid.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_rapor.layoutManager = LinearLayoutManager(this)
            rv_rapor.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Murid>) -> Unit) {
        val listMurid: ArrayList<Murid> = arrayListOf()

        if( dropdown_value_rapor_kelas.text.toString() == "" || dropdown_value_rapor_kelas.text.toString() =="Semua Kelas"){
            muridCollection.addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (result!!.isEmpty) {
                    Toast.makeText(
                        this,
                        "Tidak Ada Murid yang Terdaftar",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (document in result!!) {
                        Log.d("TugasActivity", document.toString())
                        listMurid.add(
                            Murid(
                                document.getString("avatar"),
                                document.getString("nomor"),
                                document.getString("angkatan"),
                                document.getString("nama"),
                                document.getString("kelas"),
                                document.getString("ttl"),
                                document.getString("alamat"),
                                document.getString("ayah"),
                                document.getString("ibu"),
                                document.getString("kontak_ayah"),
                                document.getString("kontak_ibu"),
                                document.getString("password")
                            )
                        )

                    }


                }
                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listMurid)
            }

        }else if( dropdown_value_rapor_kelas.text.toString() != "Semua Kelas"){
            muridCollection.whereEqualTo("kelas" ,dropdown_value_rapor_kelas.text.toString()).addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (result!!.isEmpty) {
                    Toast.makeText(
                        this,
                        "Tidak Ada Murid yang Terdaftar",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (document in result!!) {
                        Log.d("TugasActivity", document.toString())
                        listMurid.add(
                            Murid(
                                document.getString("avatar"),
                                document.getString("nomor"),
                                document.getString("angkatan"),
                                document.getString("nama"),
                                document.getString("kelas"),
                                document.getString("ttl"),
                                document.getString("alamat"),
                                document.getString("ayah"),
                                document.getString("ibu"),
                                document.getString("kontak_ayah"),
                                document.getString("kontak_ibu"),
                                document.getString("password")
                            )
                        )

                    }


                }
                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listMurid)
            }

        }
        Log.d("Tugas Activity", "getPageTugasList: after get collection data, should not be printed")
    }
}