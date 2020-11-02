package com.example.kiddleapp.Presensi

import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_rekap__presensi.*


class RekapPresensiActivity : AppCompatActivity() {
    val bulan = mutableListOf<String>()
    private val db = FirebaseFirestore.getInstance()
    val rekap: ArrayList<String> = arrayListOf()

    val tanggal_hadir : ArrayList<String> = arrayListOf()
    val tanggal_sakit: ArrayList<String> = arrayListOf()
    val tanggal_izin: ArrayList<String> = arrayListOf()
    val tanggal_alpha: ArrayList<String> = arrayListOf()
    var hadir = 0
    var izin = 0
    var sakit = 0
    var alpha = 0

//    lateinit var adapter: ArrayAdapter<String>
//     lateinit var listView: ListView
//    private lateinit var alertDialog: AlertDialog.Builder
//    lateinit var dialog: AlertDialog

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
                for (snapshot in documents) {
                    bulan.add(snapshot.id)
                }
            }


        var adapter = ArrayAdapter(this, R.layout.dropdown_text, bulan)
        (dropdown_presensi_semester.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        auto_semester.setOnItemClickListener { parent, view, position, id ->
            rekap.clear()
            tanggal_hadir.clear()
            tanggal_alpha.clear()
            tanggal_sakit.clear()
            tanggal_izin.clear()
            var item = parent.getItemAtPosition(position).toString()

            db.collection("Rekap Presensi").document(data.nomor!!)
                .collection("Bulan").document(item).collection("Tanggal")
                .addSnapshotListener { result, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (result!!.isEmpty) {
                        Toast.makeText(
                            this,
                            " Tidak Tersedia",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        for (document in result!!) {
                            rekap.add(document.getString("kehadiran")!!)
                            if (document.getString("kehadiran") == "hadir") {
                                tanggal_hadir.add(document.id)

                            }
                            if (document.getString("kehadiran") == "izin") {
                                tanggal_izin.add(document.id)
                            }
                            if (document.getString("kehadiran") == "sakit") {
                                tanggal_sakit.add(document.id)
                            }
                            if (document.getString("kehadiran") == "alpha") {
                                tanggal_alpha.add(document.id)
                            }

                        }

                        for (i in 0 until rekap.size) {
                            if (rekap[i] == "hadir") {
                                hadir += 1
                            } else if (rekap[i] == "izin") {
                                izin += 1
                            } else if (rekap[i] == "sakit") {
                                sakit += 1
                            } else if (rekap[i] == "alpha") {
                                alpha += 1
                            }
                        }
                        text_hadir.text = hadir.toString()
                        text_alpha.text = alpha.toString()
                        text_izin.text = izin.toString()
                        text_sakit.text = sakit.toString()

                        hadir = 0
                        alpha = 0
                        izin = 0
                        sakit = 0
                    }
                }
            image_hadir.setOnClickListener {
                val builder = AlertDialog.Builder(this@RekapPresensiActivity)
                if(tanggal_hadir.isEmpty()){
                    builder.setTitle("Belum Ada Data Tanggal Anak Hadir Ke Sekolah")
                }else{
                    builder.setTitle("Anak Anda Hadir Ke Sekolah Pada Tanggal : ")
                    builder.setMessage(
                        tanggal_hadir.joinToString(
                            prefix = "",
                            separator = System.lineSeparator(),
                            postfix = "",
                            transform = { it.toUpperCase() })
                    )
                }
                builder.setPositiveButton(
                    "OK"
                ) { dialog, id -> dialog.cancel() }
                builder.show()

            }

            image_sakit.setOnClickListener {
                val builder = AlertDialog.Builder(this@RekapPresensiActivity)
                if(tanggal_sakit.isEmpty()){
                    builder.setTitle("Belum Ada Data Tanggal Anak Tidak Hadir Ke Sekolah Karena Sakit")
                }else{
                    builder.setTitle("Anak Anda Tidak Hadir Ke Sekolah Karena Sakit Pada Tanggal : ")
                    builder.setMessage(
                        tanggal_sakit.joinToString(
                            prefix = "",
                            separator = System.lineSeparator(),
                            postfix = "",
                            transform = { it.toUpperCase() })
                    )

                }
                builder.setPositiveButton(
                    "OK"
                ) { dialog, id -> dialog.cancel() }
                builder.show()

            }

            image_izin.setOnClickListener {
                val builder = AlertDialog.Builder(this@RekapPresensiActivity)
                if(tanggal_izin.isEmpty()){
                    builder.setTitle("Belum Ada Data Tanggal Anak Tidak Hadir Ke Sekolah Karena Izin")
                }else{
                    builder.setTitle("Anak Anda Tidak Hadir Ke Sekolah Karena Izin Pada Tanggal : ")
                    builder.setMessage(
                        tanggal_izin.joinToString(
                            prefix = "",
                            separator = System.lineSeparator(),
                            postfix = "",
                            transform = { it.toUpperCase() })
                    )
                }

                builder.setPositiveButton(
                    "OK"
                ) { dialog, id -> dialog.cancel() }
                builder.show()
            }

            image_alpha.setOnClickListener {
                val builder = AlertDialog.Builder(this@RekapPresensiActivity)
                if(tanggal_alpha.isEmpty()){
                    builder.setTitle("Belum Ada Data Tanggal Anak Tidak Hadir Ke Sekolah Tanpa Keterangan")
                }else{
                    builder.setTitle("Anak Anda Tidak Hadir Ke Sekolah Tanpa Keterangan Pada Tanggal : ")
                    builder.setMessage(
                        tanggal_alpha.joinToString(
                            prefix = "",
                            separator = System.lineSeparator(),
                            postfix = "",
                            transform = { it.toUpperCase() })
                    )
                }

                builder.setPositiveButton(
                    "OK"
                ) { dialog, id -> dialog.cancel() }
                builder.show()
            }

            }
        }

    }


