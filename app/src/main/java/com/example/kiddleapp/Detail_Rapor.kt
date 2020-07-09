package com.example.kiddleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.kiddleapp.model.Model_Murid
import kotlinx.android.synthetic.main.activity_detail__rapor.*
import kotlinx.android.synthetic.main.activity_rapor.*

class Detail_Rapor : AppCompatActivity(), View.OnClickListener {

    var kognitif:Boolean = false
    var berbahasa:Boolean = false
    var keterampilan:Boolean = false
    var agama:Boolean = false
    var motorik:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__rapor)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Model_Murid>("data")
        tv_nama_rapor.text = data.nama
        img_avatar_rapor.setImageResource(data.avatar)
        tv_kelas_rapor.text = data.kelas

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_rapor.setOnClickListener {
            onBackPressed()
        }

        //untuk dropdown semester
        val semester = listOf("Ganjil", "Genap")
        val adapter_semester = ArrayAdapter(this, R.layout.dropdown_text, semester)
        (dropdown_rapor_semester.editText as? AutoCompleteTextView)?.setAdapter(adapter_semester)

        //untuk dropdown nilai
        val nilai = listOf("Sangat Baik", "Baik", "Cukup", "Kurang")
        val adapter_nilai = ArrayAdapter(this, R.layout.dropdown_text, nilai)
        (dropdown_kognitif_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)

        //dropdown lain tinggal ngikutin yang atas
        (dropdown_berbahasa_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        (dropdown_keterampilan_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        (dropdown_agama_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)
        (dropdown_motorik_nilai.editText as? AutoCompleteTextView)?.setAdapter(adapter_nilai)

        //set on-click listener
        expand_button_0.setOnClickListener(this)
        expand_button_1.setOnClickListener(this)
        expand_button_2.setOnClickListener(this)
        expand_button_3.setOnClickListener(this)
        expand_button_4.setOnClickListener(this)

        btn_simpan_rapor.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    //accordion sehingga hanya satu expandable layout yang aktif
    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.expand_button_0 && !kognitif){
                kognitif = true
                expandable_layout_0.expand()
                expandable_layout_1.collapse()
                expandable_layout_2.collapse()
                expandable_layout_3.collapse()
                expandable_layout_4.collapse()

                expand_button_0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_active, 0)
                expand_button_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_0 && kognitif){
                kognitif = false
                expandable_layout_0.collapse()
                expand_button_0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_1 && !berbahasa) {
                berbahasa = true
                expandable_layout_0.collapse()
                expandable_layout_1.expand()
                expandable_layout_2.collapse()
                expandable_layout_3.collapse()
                expandable_layout_4.collapse()

                expand_button_0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_active, 0)
                expand_button_2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_1 && berbahasa) {
                berbahasa = false
                expandable_layout_1.collapse()
                expand_button_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_2 && !keterampilan) {
                keterampilan = true
                expandable_layout_0.collapse()
                expandable_layout_1.collapse()
                expandable_layout_2.expand()
                expandable_layout_3.collapse()
                expandable_layout_4.collapse()

                expand_button_0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_active, 0)
                expand_button_3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_2 && keterampilan) {
                keterampilan = false
                expandable_layout_2.collapse()
                expand_button_2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_3 && !agama) {
                agama = true
                expandable_layout_0.collapse()
                expandable_layout_1.collapse()
                expandable_layout_2.collapse()
                expandable_layout_3.expand()
                expandable_layout_4.collapse()

                expand_button_0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_active, 0)
                expand_button_4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_3 && agama) {
                agama = false
                expandable_layout_3.collapse()
                expand_button_3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            } else if(v.id == R.id.expand_button_4 && !motorik) {
                motorik = true
                expandable_layout_0.collapse()
                expandable_layout_1.collapse()
                expandable_layout_2.collapse()
                expandable_layout_3.collapse()
                expandable_layout_4.expand()

                expand_button_0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
                expand_button_4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_active, 0)
            } else if(v.id == R.id.expand_button_4 && motorik) {
                motorik = false
                expandable_layout_4.collapse()
                expand_button_4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0)
            }
        }
    }
}