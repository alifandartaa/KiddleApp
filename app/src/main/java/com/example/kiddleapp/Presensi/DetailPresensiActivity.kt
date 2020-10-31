package com.example.kiddleapp.Presensi


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Presensi.Adapter.PresensiAdapter
import com.example.kiddleapp.Presensi.Model.PresensiMurid
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_presensi.*

class DetailPresensiActivity : AppCompatActivity() {
    private val murid: ArrayList<PresensiMurid> = arrayListOf()

    private val db = FirebaseFirestore.getInstance()
    private val muridCollection = db.collection("Murid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_presensi)
        supportActionBar?.hide()

        tv_judul_presensi.text = intent.getStringExtra("tanggal_saja")+" "+ intent.getStringExtra("bulan")

        murid.clear()
        showRecyclerList(murid)

        ic_back_detailpres.setOnClickListener {
            startActivity(Intent(this@DetailPresensiActivity, PresensiActivity::class.java))
        }
        

        btn_simpan_presensi.setOnClickListener {

            for (i in 0 until murid.size) {

                val presensi = hashMapOf(
                    "id_murid" to murid[i].id_murid,
                    "bulan" to intent.getStringExtra("bulan")
                )
                db.collection("Bulan Rekap Presensi").document(murid[i].id_murid!!)
                    .collection("Bulan").document(intent.getStringExtra("bulan")).set(presensi)
            }

            for (i in 0 until murid.size) {
                db.collection("Rekap Presensi").document(murid[i].id_murid!!)
                    .collection("Bulan").document(intent.getStringExtra("bulan")).collection("Tanggal")
                    .document(intent.getStringExtra("tanggal")).get()
                    .addOnSuccessListener {
                        if (!it.exists()) {
                            val presensi = hashMapOf(
                                "kehadiran" to murid[i].kehadiran
                            )
                            db.collection("Rekap Presensi").document(murid[i].id_murid!!)
                                .collection("Bulan").document(intent.getStringExtra("bulan")).collection("Tanggal")
                                .document(intent.getStringExtra("tanggal")).set(presensi)
                        } else {
                            db.collection("Rekap Presensi").document(murid[i].id_murid!!)
                                .collection("Bulan").document(intent.getStringExtra("bulan")).collection("Tanggal")
                                .document(intent.getStringExtra("tanggal"))
                                .update("kehadiran", murid[i].kehadiran)
                        }

                    }

            }


            for (i in 0 until murid.size) {
                db.collection("Presensi").document(intent.getStringExtra("kelas"))
                    .collection(intent.getStringExtra("tanggal")).document(murid[i].id_murid!!)
                    .get()
                    .addOnSuccessListener {
                        if (!it.exists()) {
                            val presensi = hashMapOf(
                                "id_murid" to murid[i].id_murid,
                                "kehadiran" to murid[i].kehadiran
                            )
                            db.collection("Presensi").document(intent.getStringExtra("kelas"))
                                .collection(intent.getStringExtra("tanggal"))
                                .document(murid[i].id_murid!!).set(presensi)

                        } else {
                            db.collection("Presensi").document(intent.getStringExtra("kelas"))
                                .collection(intent.getStringExtra("tanggal"))
                                .document(murid[i].id_murid!!)
                                .update("kehadiran", murid[i].kehadiran)
                        }
                        startActivity(Intent(this@DetailPresensiActivity, PresensiActivity::class.java))
                        finish()

                    }
            }
        }
    }

    private fun showRecyclerList(list: ArrayList<PresensiMurid>): PresensiAdapter {

        val adapter = PresensiAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")
        }

        getPageTugasList { item: ArrayList<PresensiMurid> ->
            murid.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_presensi_murid.layoutManager = LinearLayoutManager(this)
            rv_presensi_murid.adapter = adapter

        }
        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<PresensiMurid>) -> Unit) {
        val listMurid: ArrayList<PresensiMurid> = arrayListOf()
        db.collection("Presensi").document(intent.getStringExtra("kelas"))
            .collection(intent.getStringExtra("tanggal")).addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (result!!.isEmpty) {
                    muridCollection.whereEqualTo("kelas", intent.getStringExtra("kelas"))
                        .addSnapshotListener { result, e ->
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
                                    listMurid.add(
                                        PresensiMurid(
                                            document.getString("nomor"),
                                            "hadir"

                                        )
                                    )

                                }
                            }
                            Log.d("TugasActivity", "callback should be call")
                            callback.invoke(listMurid)
                        }
                } else {
                    for (document in result!!) {
                        listMurid.add(
                            PresensiMurid(
                                document.getString("id_murid"),
                                document.getString("kehadiran")
                            )
                        )

                    }
                }
                Log.d("TugasActivity", "callback should be call")
                callback.invoke(listMurid)
            }

    }
}