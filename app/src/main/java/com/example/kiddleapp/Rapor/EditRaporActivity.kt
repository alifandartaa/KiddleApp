package com.example.kiddleapp.Rapor

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_edit__rapor.*
import net.cachapa.expandablelayout.ExpandableLayout

class EditRaporActivity : AppCompatActivity(), View.OnClickListener {

    var kognitif: Boolean = false
    var berbahasa: Boolean = false
    var keterampilan: Boolean = false
    var agama: Boolean = false
    var motorik: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__rapor)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Murid>("data")

        tv_nama_rapor.text = data.nama
        img_avatar_rapor.setImageResource(data.avatar)
        tv_kelas_rapor.text = data.kelas

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_rapor.setOnClickListener {
            onBackPressed()
        }

        //untuk dropdown semester
        val semester = listOf("Ganjil", "Genap")
        val adapter_semester = ArrayAdapter(
            this,
            R.layout.dropdown_text, semester
        )
        (dropdown_rapor_semester.editText as? AutoCompleteTextView)?.setAdapter(adapter_semester)

        //untuk dropdown nilai
        val nilai = listOf("Sangat Baik", "Baik", "Cukup", "Kurang")
        val adapter_nilai = ArrayAdapter(
            this,
            R.layout.dropdown_text, nilai
        )
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

    //ketika dropdown aktif di-click
    fun nonaktif(layout:ExpandableLayout, trigger:TextView) {
        layout.collapse()
        trigger.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            R.drawable.ic_dropdown, 0
        )
    }

    //accordion sehingga hanya satu expandable layout yang aktif
    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.expand_button_0 && !kognitif) {
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
            } else if(v.id == R.id.expand_button_0 && kognitif){
                kognitif = false
                nonaktif(expandable_layout_0, expand_button_0)
            } else if(v.id == R.id.expand_button_1 && !berbahasa) {
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
            } else if(v.id == R.id.expand_button_1 && berbahasa) {
                berbahasa = false
                nonaktif(expandable_layout_1, expand_button_1)
            } else if(v.id == R.id.expand_button_2 && !keterampilan) {
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
            } else if(v.id == R.id.expand_button_2 && keterampilan) {
                keterampilan = false
                nonaktif(expandable_layout_2, expand_button_2)
            } else if(v.id == R.id.expand_button_3 && !agama) {
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
            } else if(v.id == R.id.expand_button_3 && agama) {
                agama = false
                nonaktif(expandable_layout_3, expand_button_3)
            } else if(v.id == R.id.expand_button_4 && !motorik) {
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
            } else if(v.id == R.id.expand_button_4 && motorik) {
                motorik = false
                nonaktif(expandable_layout_4, expand_button_4)
            }
        }
    }
}