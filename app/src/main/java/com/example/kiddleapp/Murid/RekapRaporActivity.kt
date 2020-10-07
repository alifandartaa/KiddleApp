package com.example.kiddleapp.Murid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.example.kiddleapp.Rapor.RaporActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit__rapor.*
import kotlinx.android.synthetic.main.activity_edit__rapor.dropdown_rapor_semester
import kotlinx.android.synthetic.main.activity_rekap_rapor.*
import net.cachapa.expandablelayout.ExpandableLayout

class RekapRaporActivity : AppCompatActivity() , View.OnClickListener  {
    var kognitif: Boolean = false
    var berbahasa: Boolean = false
    var keterampilan: Boolean = false
    var agama: Boolean = false
    var motorik: Boolean = false

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap_rapor)
        val data = intent.getParcelableExtra<Murid>("data")

        dropdown_rekap_value_rapor_semester.setText("Ganjil", false);
        dropdown_rekap_value_rapor_kelas.setText(data.kelas, false);


        db.collection("Rapor")
            .document(data.angkatan!!)
            .collection(data.kelas!! + " " + dropdown_rekap_value_rapor_semester.text.toString())
            .document(data.nomor!!).get().addOnSuccessListener {
                if (it.exists()) {
                    Toast.makeText(this, "Mengambil data dari database, mohon tunggu!", Toast.LENGTH_SHORT)
                        .show()
                }else {
                    Log.d("dok1", "No such document")
                    Toast.makeText(this, "Data Rapor Tidak Tersedia", Toast.LENGTH_SHORT)
                        .show()
                }
                dropdown_rekap_nilai_kognitif.setText(it.getString("nilai_kognitif"),false)
                input_rekap_kognitif_deskripsi.setText(it.getString("des_kognitif"))
                dropdown_rekap_nilai_berbahasa.setText(it.getString("nilai_berbahasa"),false)
                input_rekap_berbahasa_deskripsi.setText(it.getString("des_berbahasa"))
                dropdown_rekap_nilai_keterampilan.setText(it.getString("nilai_keterampilan"),false)
                input_rekap_keterampilan_deskripsi.setText(it.getString("des_keterampilan"))
                dropdown_rekap_nilai_agama.setText(it.getString("nilai_agama"),false)
                input_rekap_agama_deskripsi.setText(it.getString("des_agama"))
                dropdown_rekap_nilai_motorik.setText(it.getString("nilai_motorik"),false)
                input_rekap_motorik_deskripsi.setText(it.getString("des_motorik"))
            }




        //untuk dropdown semester
        val semester = listOf("Ganjil", "Genap")
        val adapter_semester = ArrayAdapter(this, R.layout.dropdown_text, semester)
        (dropdown_rekap_rapor_semester.editText as? AutoCompleteTextView)?.setAdapter(adapter_semester)
        dropdown_rekap_value_rapor_semester.setOnClickListener {

        }

        dropdown_rekap_value_rapor_semester.setOnItemClickListener { parent, view, position, id ->
            var item = parent.getItemAtPosition(position).toString()
            db.collection("Rapor").document(data.angkatan!!)
                .collection(dropdown_rekap_value_rapor_kelas.text.toString() + " " + item)
                .document(data.nomor!!).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        Toast.makeText(this, "Mengambil data dari database, mohon tunggu!", Toast.LENGTH_SHORT)
                            .show()

                    }
                    else{
                        Toast.makeText(this, "Data Rapor Tidak Tersedia", Toast.LENGTH_SHORT)
                            .show()
                    }
                    dropdown_rekap_nilai_kognitif.setText(it.getString("nilai_kognitif"),false)
                    input_rekap_kognitif_deskripsi.setText(it.getString("des_kognitif"))
                    dropdown_rekap_nilai_berbahasa.setText(it.getString("nilai_berbahasa"),false)
                    input_rekap_berbahasa_deskripsi.setText(it.getString("des_berbahasa"))
                    dropdown_rekap_nilai_keterampilan.setText(it.getString("nilai_keterampilan"),false)
                    input_rekap_keterampilan_deskripsi.setText(it.getString("des_keterampilan"))
                    dropdown_rekap_nilai_agama.setText(it.getString("nilai_agama"),false)
                    input_rekap_agama_deskripsi.setText(it.getString("des_agama"))
                    dropdown_rekap_nilai_motorik.setText(it.getString("nilai_motorik"),false)
                    input_rekap_motorik_deskripsi.setText(it.getString("des_motorik"))
                }
        }


        //untuk dropdown Kelas
        val kelas = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter_kelas= ArrayAdapter(this, R.layout.dropdown_text, kelas)
        (dropdown_rekap_rapor_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter_kelas)
        dropdown_rekap_value_rapor_kelas.setOnClickListener {

        }

        dropdown_rekap_value_rapor_kelas.setOnItemClickListener { parent, view, position, id ->
            var item = parent.getItemAtPosition(position).toString()
            db.collection("Rapor").document(data.angkatan!!)
                .collection(item + " " +  dropdown_rekap_value_rapor_semester.text.toString())
                .document(data.nomor!!).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        Toast.makeText(this, "Mengambil data dari database, mohon tunggu!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {

                        Toast.makeText(this, "Data Rapor Tidak Tersedia", Toast.LENGTH_SHORT)
                            .show()
                    }
                    dropdown_rekap_nilai_kognitif.setText(it.getString("nilai_kognitif"),false)
                    input_rekap_kognitif_deskripsi.setText(it.getString("des_kognitif"))
                    dropdown_rekap_nilai_berbahasa.setText(it.getString("nilai_berbahasa"),false)
                    input_rekap_berbahasa_deskripsi.setText(it.getString("des_berbahasa"))
                    dropdown_rekap_nilai_keterampilan.setText(it.getString("nilai_keterampilan"),false)
                    input_rekap_keterampilan_deskripsi.setText(it.getString("des_keterampilan"))
                    dropdown_rekap_nilai_agama.setText(it.getString("nilai_agama"),false)
                    input_rekap_agama_deskripsi.setText(it.getString("des_agama"))
                    dropdown_rekap_nilai_motorik.setText(it.getString("nilai_motorik"),false)
                    input_rekap_motorik_deskripsi.setText(it.getString("des_motorik"))
                }
        }

        img_back_detail_rekap_rapor.setOnClickListener {
            val intent: Intent =
                Intent(this@RekapRaporActivity, DetailMuridActivity::class.java).putExtra("data", data)
            startActivity(intent)
            finish()
        }

        //set on-click listener
        expand_button_6.setOnClickListener(this)
        expand_button_7.setOnClickListener(this)
        expand_button_8.setOnClickListener(this)
        expand_button_9.setOnClickListener(this)
        expand_button_10.setOnClickListener(this)


    }

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
            if (v.id == R.id.expand_button_6 && !kognitif) {
                kognitif = true
                expandable_layout_6.expand()
                expandable_layout_7.collapse()
                expandable_layout_8.collapse()
                expandable_layout_9.collapse()
                expandable_layout_10.collapse()

                expand_button_6.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_7.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_8.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_9.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_10.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown_active, 0
                )
            } else if (v.id == R.id.expand_button_6 && kognitif) {
                kognitif = false
                nonaktif(expandable_layout_6, expand_button_6)
            } else if (v.id == R.id.expand_button_7 && !berbahasa) {
                berbahasa = true
                expandable_layout_6.collapse()
                expandable_layout_7.expand()
                expandable_layout_8.collapse()
                expandable_layout_9.collapse()
                expandable_layout_10.collapse()

                expand_button_6.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_7.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_8.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_9.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_10.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown_active, 0
                )
            } else if (v.id == R.id.expand_button_7 && berbahasa) {
                berbahasa = false
                nonaktif(expandable_layout_7, expand_button_7)
            } else if (v.id == R.id.expand_button_8 && !keterampilan) {
                keterampilan = true
                expandable_layout_6.collapse()
                expandable_layout_7.collapse()
                expandable_layout_8.expand()
                expandable_layout_9.collapse()
                expandable_layout_10.collapse()

                expand_button_6.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_7.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_8.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_9.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_10.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown_active, 0
                )
            } else if (v.id == R.id.expand_button_8 && keterampilan) {
                keterampilan = false
                nonaktif(expandable_layout_8, expand_button_8)
            } else if (v.id == R.id.expand_button_9 && !agama) {
                agama = true
                expandable_layout_6.collapse()
                expandable_layout_7.collapse()
                expandable_layout_8.collapse()
                expandable_layout_9.expand()
                expandable_layout_10.collapse()

                expand_button_6.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_7.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_8.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_9.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_10.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown_active, 0
                )
            } else if (v.id == R.id.expand_button_9 && agama) {
                agama = false
                nonaktif(expandable_layout_9, expand_button_9)
            } else if (v.id == R.id.expand_button_10 && !motorik) {
                motorik = true
                expandable_layout_6.collapse()
                expandable_layout_7.collapse()
                expandable_layout_8.collapse()
                expandable_layout_9.collapse()
                expandable_layout_10.expand()

                expand_button_6.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_7.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_8.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_9.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown, 0
                )
                expand_button_10.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_dropdown_active, 0
                )
            } else if (v.id == R.id.expand_button_10 && motorik) {
                motorik = false
                nonaktif(expandable_layout_10, expand_button_10)
            }
        }
    }
}