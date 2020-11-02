package com.example.kiddleapp.Presensi

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Presensi.Model.PresensiMurid
import com.example.kiddleapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_presensi.*
import java.util.*

class PresensiActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)

    private val murid: ArrayList<PresensiMurid> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presensi)
        var sdf : String? = null
        var bulan : String? = null
        var tanggal : String?= null
        val cal = Calendar.getInstance()


        btn_selanjutnya_presensi.setOnClickListener {
            btn_selanjutnya_presensi.isEnabled = false
            if(sdf==null ||  auto_dropdown_kelas_presensi.text.toString().isNullOrEmpty() ){
                Toast.makeText(
                    this,
                    "Silahkan pilih Kelas dan Tanggal",
                    Toast.LENGTH_SHORT
                ).show()
                btn_selanjutnya_presensi.isEnabled = true
            }else{
                val intent = Intent(this@PresensiActivity, DetailPresensiActivity::class.java)
                intent.putExtra("kelas", auto_dropdown_kelas_presensi.text.toString())
                intent.putExtra("tanggal", sdf)
                intent.putExtra("bulan", bulan)
                intent.putExtra("tanggal_saja", tanggal)
                startActivity(intent)
            }

        }



        btn_libur_presensi.setOnClickListener {
            if (sdf == null || auto_dropdown_kelas_presensi.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Silahkan pilih Kelas dan Tanggal",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                db.collection("Presensi").document(auto_dropdown_kelas_presensi.text.toString())
                    .collection(sdf!!).addSnapshotListener { result, e ->
                        if (e != null) {
                            return@addSnapshotListener
                        }
                        if (result!!.isEmpty) {
                            db.collection("Murid").whereEqualTo(
                                "kelas",
                                auto_dropdown_kelas_presensi.text.toString()
                            )
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
                                            murid.add(
                                                PresensiMurid(
                                                    document.getString("nomor"),
                                                    "libur"
                                                )
                                            )

                                        }
                                    }
                                }
                        } else {
                            for (document in result!!) {
                                murid.add(
                                    PresensiMurid(
                                        document.getString("id_murid"),
                                        document.getString("kehadiran")
                                    )
                                )

                            }
                        }
                        Log.d("TugasActivity", "callback should be call")
                    }

                val popup: PopupMenu = PopupMenu(this, it)
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Hari Libur")
                    setMessage(
                        "Apakah Tanggal " + sdf + " Merupakan Hari Libur?"
                    )
                    setPositiveButton("Ya") { _, _ ->
                        for (i in 0 until murid.size) {
                            db.collection("Rekap Presensi").document(murid[i].id_murid!!)
                                .collection("Bulan").document(bulan!!).collection("Tanggal")
                                .document(sdf!!).get()
                                .addOnSuccessListener {
                                    if (!it.exists()) {
                                        val presensi = hashMapOf(
                                            "kehadiran" to murid[i].kehadiran
                                        )
                                        db.collection("Rekap Presensi").document(murid[i].id_murid!!)
                                            .collection("Bulan").document(bulan!!).collection("Tanggal")
                                            .document(sdf!!).set(presensi)
                                    } else {
                                        db.collection("Rekap Presensi").document(murid[i].id_murid!!)
                                            .collection("Bulan").document(bulan!!).collection("Tanggal")
                                            .document(sdf!!)
                                            .update("kehadiran", murid[i].kehadiran)
                                    }

                                }

                        }


                        for (i in 0 until murid.size) {
                            db.collection("Presensi").document(auto_dropdown_kelas_presensi.text.toString())
                                .collection(sdf!!).document(murid[i].id_murid!!)
                                .get()
                                .addOnSuccessListener {
                                    if (!it.exists()) {
                                        val presensi = hashMapOf(
                                            "id_murid" to murid[i].id_murid,
                                            "kehadiran" to murid[i].kehadiran
                                        )
                                        db.collection("Presensi").document(
                                            auto_dropdown_kelas_presensi.text.toString()
                                        )
                                            .collection(sdf!!)
                                            .document(murid[i].id_murid!!).set(presensi)

                                    } else {
                                        db.collection("Presensi").document(
                                            auto_dropdown_kelas_presensi.text.toString()
                                        )
                                            .collection(sdf!!)
                                            .document(murid[i].id_murid!!)
                                            .update("kehadiran", murid[i].kehadiran)
                                    }
                                    startActivity(
                                        Intent(
                                            this@PresensiActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()

                                }
                        }

                    }

                    setNegativeButton("Tidak") { _, _ -> }
                }.show()
            }
        }


        ic_back_presensi.setOnClickListener {
            startActivity(Intent(this@PresensiActivity, MainActivity::class.java))
        }

        //Tanggal
        dp_tanggal_presensi.init(
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            val month = monthOfYear + 1
            sdf = "$dayOfMonth-$month-$year"
            tanggal = "$dayOfMonth"
            if("$month" == "1"){
                bulan = "Januari " + "$year"
            }else if("$month" == "2"){
                bulan = "Februari " + "$year"
            }else if("$month" == "3"){
                bulan = "Maret " + "$year"
            }else if("$month" == "4"){
                bulan = "April " + "$year"
            }else if("$month" == "5"){
                bulan = "Mei " + "$year"
            }else if("$month" == "6"){
                bulan = "Juni " + "$year"
            }else if("$month" == "7"){
                bulan = "Juli " + "$year"
            }else if("$month" == "8"){
                bulan = "Agustus " + "$year"
            }else if("$month" == "9"){
                bulan = "September " + "$year"
            }else if("$month" == "10"){
                bulan = "Oktober " + "$year"
            }else if("$month" == "11"){
                bulan = "November " + "$year"
            }else if("$month" == "112") {
                bulan = "Desember " + "$year"
            }
        }
        dp_tanggal_presensi.setMaxDate(System.currentTimeMillis());

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)
        if(sharedPreferences.getString("kelas", "") == "admin") {
            //adapter untuk dropdown presensi
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_kelas_presensi.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }else {
            dropdown_kelas_presensi.isEnabled = true
            auto_dropdown_kelas_presensi.setText(sharedPreferences.getString("kelas", ""))
        }


        supportActionBar?.hide()
    }

}