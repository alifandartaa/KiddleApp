package com.example.kiddleapp.Rapor

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Murid.Adapter.MuridAdapter
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_rapor.*

class RaporActivity : AppCompatActivity() {

    //untuk menyimpan murid
    private var murid = ArrayList<Murid>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rapor)

        //untuk dropdown. bisa ganti kelas yang ada di firebase nya untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_text, items
        )
        (dropdown_rapor_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        rv_rapor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        murid.clear()

        //bisa diganti dengan data dari firebase
        val temp = Murid(
            R.drawable.avatar, "198022", "Lee Ji Eun", "Bintang Besar",
            "Bandung, 3 Mei 1999", "Jl. Watugong No.17F", "Budi", "Siti",
            "0812345678", "089765432", "mangga123"
        )
        murid.add(temp)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_rapor.adapter =
            MuridAdapter(murid) {
                val intent: Intent =
                    Intent(this@RaporActivity, EditRaporActivity::class.java).putExtra("data", it)
                startActivity(intent)
            }

        //intent untuk kembali ke halaman sebelumnya
        img_back_rapor.setOnClickListener {
            onBackPressed()
        }
    }
}