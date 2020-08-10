package com.example.kiddleapp.Tugas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Adapter.TugasAdapter
import com.example.kiddleapp.Tugas.Model.Tugas
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tugas.*
import java.security.AccessController.getContext

class TugasActivity : AppCompatActivity() {

    //untuk menyimpan TugasActivity
    // private var tugas = ArrayList<Tugas>()
    private val tugas: ArrayList<Tugas> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val tugasCollection = db.collection("Tugas")

    private lateinit var gambar: String
    private lateinit var isi: String
    private lateinit var jam: String
    private lateinit var judul: String
    private lateinit var jumlah: String
    private lateinit var kelas: String
    private lateinit var link: String
    private lateinit var tanggal: String
    private lateinit var video: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tugas)

        //untuk dropdown
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_tugas_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        Log.d("Tugas Activity", "onCreate: before show recycler tugas")
        showRecyclerList(tugas)
        Log.d("Tugas Activity", "onCreate: after show recycler tugas")

        //recyclerView murid
        //rv_tugas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        //mengkosongkan isi arraylist
        //tugas.clear()

        //mengambil data


        //bisa diganti dengan data dari firebase
//        val temp = Tugas(
//            "Bintang A",
//            "Keterampilan",
//            "Kerjakan Soal pada akhir video",
//            "20 Juli 2020",
//            "20.20 WIB",
//            "20/20",
//            0,
//            R.raw.video_tugas,
//            "https://youtu.be/g9aXIpJFKyU"
//        )
//        tugas.add(temp)
//
//        val temp2 = Tugas(
//            "Bintang A",
//            "Keterampilan",
//            "Kerjakan Soal pada akhir video",
//            "20 Juli 2020",
//            "20.20 WIB",
//            "20/20",
//            R.drawable.banner_tugas,
//            0,
//            "https://youtu.be/g9aXIpJFKyU"
//        )
//        tugas.add(temp2)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
//        rv_tugas.adapter =
//            TugasAdapter(tugas) {
//
//                val intent: Intent =
//                    Intent(this@TugasActivity, DetailTugasActivity::class.java).putExtra("data", it)
//                startActivity(intent)
//            }

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



    private fun showRecyclerList(list: ArrayList<Tugas>): TugasAdapter {
        val adapter = TugasAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")
            val intent: Intent =
                Intent(this@TugasActivity, DetailTugasActivity::class.java).putExtra("data", it)
                startActivity(intent)
        }

        getPageTugasList { item: ArrayList<Tugas> ->
            tugas.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_tugas.layoutManager = LinearLayoutManager(this)
            rv_tugas.adapter = adapter

        }


        return adapter
    }

    private fun getPageTugasList(callback: (item: ArrayList<Tugas>) -> Unit) {
        val listTugas: ArrayList<Tugas> = arrayListOf()
        Log.d("Tugas Activity", "getPageTugasList: before get collection data")
        tugasCollection.addSnapshotListener { result, e ->
            if (e != null) {
                Log.w("Tugas Activity", "listen:error", e)
                return@addSnapshotListener
            }

            for (document in result!!) {
                Log.d("TugasActivity", document.toString())
                listTugas.add(
                    Tugas(
                        document.id,
                        document.getString("kelas"),
                        document.getString("judul"),
                        document.getString("isi"),
                        document.getString("tanggal"),
                        document.getString("jam"),
                        document.getString("jumlah"),
                        document.getString("gambar"),
                        document.getString("video"),
                        document.getString("link")
                    )
                )
            }

            Log.d("TugasActivity", "callback should be call")
            callback.invoke(listTugas)
        }
        Log.d("Tugas Activity", "getPageTugasList: after get collection data, should not be printed")
    }
}




