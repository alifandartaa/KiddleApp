package com.example.kiddleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Jurnal
import com.example.kiddleapp.adapter.Adapter_Murid
import com.example.kiddleapp.adapter.Adapter_Tugas
import com.example.kiddleapp.model.Model_Jurnal
import com.example.kiddleapp.model.Model_Murid
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.activity_jurnal.*
import kotlinx.android.synthetic.main.activity_rapor.*
import kotlinx.android.synthetic.main.activity_rapor.img_back_rapor
import kotlinx.android.synthetic.main.activity_tugas.*

class Jurnal : AppCompatActivity() {

    //untuk menyimpan Jurnal
    private var jurnal = ArrayList<Model_Jurnal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jurnal)

        //untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_jurnal_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        rv_jurnal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        jurnal.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Jurnal("Motorik", "Bintang A", "Belajar Menghitung", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget.", "20 Juli 2020", 0, R.raw.video_tugas)
        jurnal.add(temp)

        val temp2 = Model_Jurnal( "Motorik", "Bintang A", "Belajar Menghitung", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget.", "20 Juli 2020", R.drawable.banner_jurnal, 0)
        jurnal.add(temp2)

        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_jurnal.adapter = Adapter_Jurnal(jurnal){
            val intent: Intent = Intent(this@Jurnal, Detail_Jurnal::class.java).putExtra("data", it)
            startActivity(intent)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_jurnal.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_jurnal.setOnClickListener {
            val intent: Intent = Intent(this@Jurnal, Edit_Jurnal::class.java).putExtra("jenis","TAMBAH_JURNAL")
            startActivity(intent)
        }

    }
}