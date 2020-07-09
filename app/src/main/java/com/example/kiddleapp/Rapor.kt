package com.example.kiddleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Murid
import com.example.kiddleapp.model.Model_Murid
import kotlinx.android.synthetic.main.activity_rapor.*

class Rapor : AppCompatActivity() {

    //untuk menyimpan murid
    private var murid = ArrayList<Model_Murid>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rapor)

        //untuk dropdown
        val items = listOf("Ganjil", "Genap")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_container_rapor.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        rv_rapor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        murid.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Murid(R.drawable.avatar, "Lee Ji Eun")
        murid.add(temp)

        val temp2 = Model_Murid(R.drawable.avatar, "IU")
        murid.add(temp2)

        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_rapor.adapter = Adapter_Murid(murid){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_rapor.setOnClickListener {
            onBackPressed()
        }
    }
}