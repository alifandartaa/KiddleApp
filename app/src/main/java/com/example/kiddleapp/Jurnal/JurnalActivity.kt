package com.example.kiddleapp.Jurnal

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Jurnal.Adapter.JurnalAdapter
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_jurnal.*

class JurnalActivity : AppCompatActivity() {

    //untuk menyimpan JurnalActivity
    private var jurnal = ArrayList<Jurnal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jurnal)

        //untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_text, items
        )
        (dropdown_jurnal_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        rv_jurnal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        jurnal.clear()

        //bisa diganti dengan data dari firebase
        val temp = Jurnal(
            "Motorik",
            "Bintang A",
            "Belajar Menghitung",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget.",
            "20 Juli 2020",
            0,
            R.raw.video_tugas
        )
        jurnal.add(temp)

        val temp2 = Jurnal(
            "Motorik",
            "Bintang A",
            "Belajar Menghitung",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget.",
            "20 Juli 2020",
            R.drawable.banner_jurnal,
            0
        )
        jurnal.add(temp2)

        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_jurnal.adapter =
            JurnalAdapter(jurnal) {
                val intent: Intent =
                    Intent(this@JurnalActivity, DetailJurnalActivity::class.java).putExtra(
                        "data",
                        it
                    )
                startActivity(intent)
            }

        //intent untuk kembali ke halaman sebelumnya
        img_back_jurnal.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_jurnal.setOnClickListener {
            val intent: Intent =
                Intent(this@JurnalActivity, EditJurnalActivity::class.java).putExtra(
                    "jenis",
                    "TAMBAH_JURNAL"
                )
            startActivity(intent)
        }

    }
}