package com.example.kiddleapp.Rapor

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit__murid.*
import kotlinx.android.synthetic.main.activity_edit__rapor.*
import kotlinx.android.synthetic.main.activity_edit_jurnal.*
import kotlinx.android.synthetic.main.activity_jurnal.*
import kotlinx.android.synthetic.main.activity_profil_sekolah.*
import net.cachapa.expandablelayout.ExpandableLayout
import java.util.*

class EditRaporActivity : AppCompatActivity(), View.OnClickListener {

    var kognitif: Boolean = false
    var berbahasa: Boolean = false
    var keterampilan: Boolean = false
    var agama: Boolean = false
    var motorik: Boolean = false

    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__rapor)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Murid>("data")
        tv_nama_rapor.text = data.nama
        Glide.with(this).load(data.avatar).centerCrop().into(img_avatar_rapor)
        tv_kelas_rapor.text = data.kelas

        //untuk dropdown nilai
        val nilai = listOf("Sangat Baik", "Baik", "Cukup", "Kurang")
        val adapter_nilai = ArrayAdapter(this, R.layout.dropdown_text, nilai)
        (dropdown_kognitif_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        //dropdown lain tinggal ngikutin yang atas
        (dropdown_berbahasa_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        (dropdown_keterampilan_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        (dropdown_agama_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        (dropdown_motorik_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)

        dropdown_value_rapor_semester.setText("Ganjil", false);

        db.collection("Rapor")
            .document(data.angkatan!!)
            .collection(data.kelas!! + " " + dropdown_value_rapor_semester.text.toString())
            .document(data.nomor!!).get().addOnSuccessListener {
                if (it != null) {
                    dropdown_nilai_kognitif.setText(it.getString("nilai_kognitif"),false)
                    input_kognitif_deskripsi.setText(it.getString("des_kognitif"))
                    dropdown_nilai_berbahasa.setText(it.getString("nilai_berbahasa"),false)
                    input_berbahasa_deskripsi.setText(it.getString("des_berbahasa"))
                    dropdown_nilai_keterampilan.setText(it.getString("nilai_keterampilan"),false)
                    input_keterampilan_deskripsi.setText(it.getString("des_keterampilan"))
                    dropdown_nilai_agama.setText(it.getString("nilai_agama"),false)
                    input_agama_deskripsi.setText(it.getString("des_agama"))
                    dropdown_nilai_motorik.setText(it.getString("nilai_motorik"),false)
                    input_motorik_deskripsi.setText(it.getString("des_motorik"))
                }
            }


        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_rapor.setOnClickListener {
            val intent: Intent = Intent(this@EditRaporActivity, RaporActivity::class.java)
            startActivity(intent)
        }

        //untuk dropdown semester
        val semester = listOf("Ganjil", "Genap")
        val adapter_semester = ArrayAdapter(this, R.layout.dropdown_text, semester)
        (dropdown_rapor_semester.editText as? AutoCompleteTextView)?.setAdapter(adapter_semester)
        dropdown_value_rapor_semester.setOnClickListener {
            Toast.makeText(this, "Mengambil data dari database, mohon tunggu!", Toast.LENGTH_SHORT)
                .show()
        }

        dropdown_value_rapor_semester.setOnItemClickListener { parent, view, position, id ->
            var item = parent.getItemAtPosition(position).toString()
            db.collection("Rapor").document(data.angkatan!!)
                .collection(data.kelas!! + " " + item)
                .document(data.nomor!!).get()
                .addOnSuccessListener {
                    if (it != null) {
                        dropdown_nilai_kognitif.setText(it.getString("nilai_kognitif"),false)
                        input_kognitif_deskripsi.setText(it.getString("des_kognitif"))
                        dropdown_nilai_berbahasa.setText(it.getString("nilai_berbahasa"),false)
                        input_berbahasa_deskripsi.setText(it.getString("des_berbahasa"))
                        dropdown_nilai_keterampilan.setText(it.getString("nilai_keterampilan"),false)
                        input_keterampilan_deskripsi.setText(it.getString("des_keterampilan"))
                        dropdown_nilai_agama.setText(it.getString("nilai_agama"),false)
                        input_agama_deskripsi.setText(it.getString("des_agama"))
                        dropdown_nilai_motorik.setText(it.getString("nilai_motorik"),false)
                        input_motorik_deskripsi.setText(it.getString("des_motorik"))
                    }
                }
        }



        //set on-click listener
        expand_button_0.setOnClickListener(this)
        expand_button_1.setOnClickListener(this)
        expand_button_2.setOnClickListener(this)
        expand_button_3.setOnClickListener(this)
        expand_button_4.setOnClickListener(this)

        btn_simpan_rapor.setOnClickListener {
            btn_simpan_rapor.isEnabled = false
            btn_simpan_rapor.text = "Loading"

            if (dropdown_value_rapor_semester.text.toString().isEmpty()) {
                Toast.makeText(this, "Harap Pilih Semester", Toast.LENGTH_SHORT).show()
                btn_simpan_rapor.isEnabled = true
                btn_simpan_rapor.text = "Simpan"
            } else if (dropdown_nilai_kognitif.text.toString()
                    .isNullOrEmpty() || input_kognitif_deskripsi.text.toString().isNullOrEmpty()
                || dropdown_nilai_berbahasa.text.toString()
                    .isNullOrEmpty() || input_berbahasa_deskripsi.text.toString().isNullOrEmpty()
                || dropdown_nilai_keterampilan.text.toString()
                    .isNullOrEmpty() || input_keterampilan_deskripsi.text.toString().isNullOrEmpty()
                || dropdown_nilai_agama.text.toString().isNullOrEmpty()
                || input_agama_deskripsi.text.toString()
                    .isNullOrEmpty() || dropdown_nilai_motorik.text.toString().isNullOrEmpty()
                || input_motorik_deskripsi.text.toString().isNullOrEmpty()
            ) {

                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
                btn_simpan_rapor.isEnabled = true
                btn_simpan_rapor.text = "Simpan"
            } else {
                val rapor = hashMapOf(
                    "id_murid" to "",
                    "kelas" to "",
                    "semester" to "",
                    "nilai_kognitif" to "",
                    "des_kognitif" to "",
                    "nilai_berbahasa" to "",
                    "des_berbahasa" to "",
                    "nilai_keterampilan" to "",
                    "des_keterampilan" to "",
                    "nilai_agama" to "",
                    "des_agama" to "",
                    "nilai_motorik" to "",
                    "des_motorik" to ""
                )
                db.collection("Rapor").document(data.angkatan!!)
                    .collection(data.kelas!! + " " + dropdown_value_rapor_semester.text.toString())
                    .document(data.nomor!!).set(rapor).addOnCompleteListener {
                        db.collection("Rapor").document(data.angkatan!!)
                            .collection(data.kelas!! + " " + dropdown_value_rapor_semester.text.toString())
                            .document(data.nomor!!)
                            .update(
                                mapOf(
                                    "id_murid" to data.nomor,
                                    "kelas" to data.kelas,
                                    "semester" to dropdown_value_rapor_semester.text.toString(),
                                    "nilai_kognitif" to dropdown_nilai_kognitif.text.toString(),
                                    "des_kognitif" to input_kognitif_deskripsi.text.toString(),
                                    "nilai_berbahasa" to dropdown_nilai_berbahasa.text.toString(),
                                    "des_berbahasa" to input_berbahasa_deskripsi.text.toString(),
                                    "nilai_keterampilan" to dropdown_nilai_keterampilan.text.toString(),
                                    "des_keterampilan" to input_keterampilan_deskripsi.text.toString(),
                                    "nilai_agama" to dropdown_nilai_agama.text.toString(),
                                    "des_agama" to input_agama_deskripsi.text.toString(),
                                    "nilai_motorik" to dropdown_nilai_motorik.text.toString(),
                                    "des_motorik" to input_motorik_deskripsi.text.toString()
                                )
                            ).addOnCompleteListener {
                                val intent: Intent =
                                    Intent(
                                        this@EditRaporActivity,
                                        RaporActivity::class.java
                                    )
                                startActivity(intent)
                                Toast.makeText(
                                    this,
                                    "Simpan Berhasil",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                    }

            }

        }
    }

        //ketika dropdown aktif di-click
        fun nonaktif(layout: ExpandableLayout, trigger: TextView) {
            layout.collapse()
            trigger.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                R.drawable.ic_dropdown, 0
            )
        }

        //accordion sehingga hanya satu expandable layout yang aktif
        override fun onClick(v: View?) {
            if (v != null) {
                if (v.id == R.id.expand_button_0 && !kognitif) {
                    kognitif = true
                    expandable_layout_0.expand()
                    expandable_layout_1.collapse()
                    expandable_layout_2.collapse()
                    expandable_layout_3.collapse()
                    expandable_layout_4.collapse()

                    expand_button_0.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown_active, 0
                    )
                    expand_button_1.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_3.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_4.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                } else if (v.id == R.id.expand_button_0 && kognitif) {
                    kognitif = false
                    nonaktif(expandable_layout_0, expand_button_0)
                } else if (v.id == R.id.expand_button_1 && !berbahasa) {
                    berbahasa = true
                    expandable_layout_0.collapse()
                    expandable_layout_1.expand()
                    expandable_layout_2.collapse()
                    expandable_layout_3.collapse()
                    expandable_layout_4.collapse()

                    expand_button_0.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_1.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown_active, 0
                    )
                    expand_button_2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_3.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_4.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                } else if (v.id == R.id.expand_button_1 && berbahasa) {
                    berbahasa = false
                    nonaktif(expandable_layout_1, expand_button_1)
                } else if (v.id == R.id.expand_button_2 && !keterampilan) {
                    keterampilan = true
                    expandable_layout_0.collapse()
                    expandable_layout_1.collapse()
                    expandable_layout_2.expand()
                    expandable_layout_3.collapse()
                    expandable_layout_4.collapse()

                    expand_button_0.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_1.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown_active, 0
                    )
                    expand_button_3.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_4.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                } else if (v.id == R.id.expand_button_2 && keterampilan) {
                    keterampilan = false
                    nonaktif(expandable_layout_2, expand_button_2)
                } else if (v.id == R.id.expand_button_3 && !agama) {
                    agama = true
                    expandable_layout_0.collapse()
                    expandable_layout_1.collapse()
                    expandable_layout_2.collapse()
                    expandable_layout_3.expand()
                    expandable_layout_4.collapse()

                    expand_button_0.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_1.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_3.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown_active, 0
                    )
                    expand_button_4.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                } else if (v.id == R.id.expand_button_3 && agama) {
                    agama = false
                    nonaktif(expandable_layout_3, expand_button_3)
                } else if (v.id == R.id.expand_button_4 && !motorik) {
                    motorik = true
                    expandable_layout_0.collapse()
                    expandable_layout_1.collapse()
                    expandable_layout_2.collapse()
                    expandable_layout_3.collapse()
                    expandable_layout_4.expand()

                    expand_button_0.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_1.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_3.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown, 0
                    )
                    expand_button_4.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_dropdown_active, 0
                    )
                } else if (v.id == R.id.expand_button_4 && motorik) {
                    motorik = false
                    nonaktif(expandable_layout_4, expand_button_4)
                }
            }
        }
    }
