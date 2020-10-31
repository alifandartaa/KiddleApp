package com.example.kiddleapp.Spp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.Model.Pembayaran
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_pembayaran.*
import kotlinx.android.synthetic.main.activity_edit_pembayaran.*

class EditPembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pembayaran)

        val db = FirebaseFirestore.getInstance()
        val data = intent.getParcelableExtra<Pembayaran>("data")
        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)

        if(intent.getStringExtra("jenis") == "EDIT"){
            ic_back_edit_pembayaran.setOnClickListener {
                val intent:Intent = Intent(this@EditPembayaranActivity, DetailPembayaranActivity::class.java).putExtra("data", data)
                startActivity(intent)
                finish()
            }

            dropdown_value_semester_edit.setText(data.semester)
            dropdown_value_jumlah_edit.setText(data.harga)
            dropdown_value_batas_edit.setText(data.batas)
            dropdown_value_bulan_edit.setText(data.bulan)

            dropdown_value_semester_edit.isEnabled = false
            dropdown_value_bulan_edit.isEnabled = false
        } else {
            ic_back_edit_pembayaran.setOnClickListener {
                onBackPressed()
            }

            val semester = listOf("Ganjil", "Genap")
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, semester)
            (dropdown_semester_edit.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }

        btn_simpan_pembayaran.setOnClickListener {
            btn_simpan_pembayaran.isEnabled = false
            btn_simpan_pembayaran.text = "Loading"

            if(dropdown_value_semester_edit.text.toString().isEmpty() ||
               dropdown_value_jumlah_edit.text.toString().isEmpty() ||
               dropdown_value_batas_edit.text.toString().isEmpty() ||
               dropdown_value_bulan_edit.text.toString().isEmpty()){
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                btn_simpan_pembayaran.isEnabled = true
                btn_simpan_pembayaran.text = "Simpan"
            } else{
                db.document("SPP/${dropdown_value_bulan_edit.text.toString()}").set(mapOf(
                    "batas" to dropdown_value_batas_edit.text.toString(),
                    "bulan" to dropdown_value_bulan_edit.text.toString(),
                    "harga" to dropdown_value_jumlah_edit.text.toString(),
                    "semester" to dropdown_value_semester_edit.text.toString()
                )).addOnCompleteListener {
                    val intent:Intent = Intent(this@EditPembayaranActivity, MainActivity::class.java).putExtra("jenis", "pembayaran")
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}