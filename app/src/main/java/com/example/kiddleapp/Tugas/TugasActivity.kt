package com.example.kiddleapp.Tugas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Adapter.TugasAdapter
import com.example.kiddleapp.Tugas.Model.Tugas
import kotlinx.android.synthetic.main.activity_tugas.*

class TugasActivity : AppCompatActivity() {

    //untuk menyimpan TugasActivity
    private var tugas = ArrayList<Tugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tugas)

        //untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_text, items
        )
        (dropdown_tugas_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //recyclerView murid
        rv_tugas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        tugas.clear()

        //bisa diganti dengan data dari firebase
        val temp = Tugas(
            "Bintang A",
            "Keterampilan",
            "Kerjakan Soal pada akhir video",
            "20 Juli 2020",
            "20.20 WIB",
            "20/20",
            0,
            R.raw.video_tugas,
            "https://youtu.be/g9aXIpJFKyU"
        )
        tugas.add(temp)

        val temp2 = Tugas(
            "Bintang A",
            "Keterampilan",
            "Kerjakan Soal pada akhir video",
            "20 Juli 2020",
            "20.20 WIB",
            "20/20",
            R.drawable.banner_tugas,
            0,
            "https://youtu.be/g9aXIpJFKyU"
        )
        tugas.add(temp2)
        val temp3 = Tugas(
            "Bintang A",
            "Keterampilan",
            "Kerjakan Soal pada akhir video",
            "20 Juli 2020",
            "20.20 WIB",
            "20/20",
            0,
            R.raw.video_tugas,
            ""
        )
        tugas.add(temp3)

        val temp4 = Tugas(
            "Bintang A",
            "Keterampilan",
            "Kerjakan Soal pada akhir video",
            "20 Juli 2020",
            "20.20 WIB",
            "20/20",
            R.drawable.banner_tugas,
            0,
            "https://id.quora.com/Apa-padanan-bahasa-Indonesia-untuk-link-istilah-Internet#:~:text=Jika%20link%20yang%20dimaksud%20adalah,berarti%20hubungan%20dengan%20yang%20lain."
        )
        tugas.add(temp4)
        val temp5 = Tugas(
            "Bintang A",
            "Keterampilan",
            "Kerjakan Soal pada akhir video",
            "20 Juli 2020",
            "20.20 WIB",
            "20/20",
            0,
            R.raw.video_tugas,
            ""
        )
        tugas.add(temp5)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_tugas.adapter =
            TugasAdapter(tugas) {
                val intent: Intent =
                    Intent(this@TugasActivity, DetailTugasActivity::class.java).putExtra("data", it)
                startActivity(intent)
            }

        //intent untuk kembali ke halaman sebelumnya
        img_back_tugas.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_tugas.setOnClickListener {
            val intent: Intent = Intent(this@TugasActivity, EditTugasActivity::class.java).putExtra(
                "jenis",
                "TAMBAH_TUGAS"
            )
            startActivity(intent)
        }

    }
}