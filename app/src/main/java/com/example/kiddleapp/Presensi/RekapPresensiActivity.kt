package com.example.kiddleapp.Presensi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_rekap__presensi.*

class RekapPresensiActivity : AppCompatActivity() {
    val bulan = mutableListOf<String>()
    private val db = FirebaseFirestore.getInstance()
    val rekap: ArrayList<String> = arrayListOf()
    var hadir = 0
    var izin = 0
    var sakit = 0
    var alpha = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap__presensi)

        text_hadir.text = hadir.toString()
        text_alpha.text = alpha.toString()
        text_izin.text = izin.toString()
        text_sakit.text = sakit.toString()

        val data = intent.getParcelableExtra<Murid>("data")

        img_back_presensi.setOnClickListener {
          onBackPressed()
            finish()
        }

        db.collection("Bulan Rekap Presensi").document(data.nomor!!)
            .collection("Bulan").get().addOnSuccessListener { documents ->
                Log.d("masuk1", documents.size().toString())
                for (snapshot in documents) {
                    bulan.add(snapshot.id)
                    Log.d("masuk1", snapshot.id)
                }
            }


        val adapter = ArrayAdapter(this, R.layout.dropdown_text, bulan)
        (dropdown_presensi_semester.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        auto_semester.setOnItemClickListener { parent, view, position, id ->
            rekap.clear()

            var item = parent.getItemAtPosition(position).toString()

            db.collection("Rekap Presensi").document(data.nomor!!)
                .collection("Bulan").document(item).collection("Tanggal")
                .addSnapshotListener { result, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if(result!!.isEmpty){
                        Toast.makeText(
                            this,
                            " Tidak Tersedia",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }else {
                        for (document in result!!) {
                            rekap.add(document.getString("kehadiran")!!)
                        }

                    }
                    Log.d("hitung", rekap.size.toString())
                    for(i in 0 until rekap.size){
                        if(rekap[i]=="hadir"){
                            hadir += 1
                        }else if(rekap[i]=="izin"){
                            izin += 1
                        }else if(rekap[i]=="sakit"){
                            sakit += 1
                        }else if(rekap[i]=="alpha") {
                            alpha += 1
                        }
                        Log.d("hitung", hadir.toString())
                    }
                    text_hadir.text = hadir.toString()
                    text_alpha.text = alpha.toString()
                    text_izin.text = izin.toString()
                    text_sakit.text = sakit.toString()

                    hadir=0
                    alpha=0
                    izin=0
                    sakit=0
                }


        }
    }
}