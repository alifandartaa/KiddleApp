package com.example.kiddleapp.Tugas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kiddleapp.Pengumuman.PengumumanActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Adapter.HasilTugasAdapter
import com.example.kiddleapp.Tugas.Adapter.TugasAdapter
import com.example.kiddleapp.Tugas.Model.HasilTugas
import com.example.kiddleapp.Tugas.Model.Tugas
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail_tugas.*
import kotlinx.android.synthetic.main.activity_tugas.*

class DetailTugasActivity : AppCompatActivity() {

    //untuk menyimpan Hasil TugasActivity

    private val hasilTugas: ArrayList<HasilTugas> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private val hasilTugasCollection = db.collection("Hasil Tugas")
    var storage = FirebaseStorage.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas)
        showRecyclerList(hasilTugas)
        //mengambil data dari halaman sebelumnya

       val data = intent.getParcelableExtra<Tugas>("data")

        tv_kelas_detail_tugas.text = data.kelas
        tv_judul_detail_tugas.text = data.judul
        tv_isi_detail_tugas.text = data.isi
        tv_tanggal_detail_tugas.text=data.tanggal
        tv_jam_detail_tugas.text=data.jam
        tv_link_detail_tugas.text=data.link

        if(data.gambar!= "") {
            img_detail_tugas.visibility = View.VISIBLE
            vv_detail_tugas.visibility = View.GONE
            Glide.with(this).load(data.gambar).centerCrop().into(img_detail_tugas)
            //img_detail_tugas.setImageResource(data.gambar)
        }
        else if(data.video!="") {
            vv_detail_tugas.visibility = View.VISIBLE
            img_detail_tugas.visibility = View.GONE
            vv_detail_tugas.setVideoURI(Uri.parse( data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_tugas.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_tugas)
            vv_detail_tugas.seekTo(10)

        }

        if(data.link!="") {
            img_link_detail_tugas.visibility = View.VISIBLE
            tv_link_detail_tugas.visibility = View.VISIBLE

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_tugas.setOnClickListener {
            val intent: Intent =  Intent(this@DetailTugasActivity,TugasActivity::class.java  )
            startActivity(intent)
        }

        // menu
        menu_detail_tugas.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    //val intent: Intent = Intent(this@DetailTugasActivity, EditTugasActivity::class.java)
                    val intent: Intent = Intent(
                        this@DetailTugasActivity,
                        EditTugasActivity::class.java
                    ).putExtra("jenis", "EDIT_TUGAS")
                    intent.putExtra("data", data)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                else if(it.itemId == R.id.menu_hapus2) {
                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Hapus Tugas")
                        setMessage(
                            "Apa anda yakin ingin menghapus Tugas ini?"
                        )
                        setPositiveButton("Ya") { _, _ ->
                            db.collection("Tugas").document(data.id_tugas!!).delete()
                                .addOnSuccessListener {
                                    storage.child("Tugas/"+data.id_tugas!!+"/"+data.id_tugas!!+".jpg").delete().addOnSuccessListener {

                                    }
                                    storage.child("Tugas/"+data.id_tugas!!+"/"+data.id_tugas!!+".jpeg").delete().addOnSuccessListener {

                                    }
                                    storage.child("Tugas/"+data.id_tugas!!+"/"+data.id_tugas!!+".png").delete().addOnSuccessListener {

                                    }
                                    storage.child("Tugas/"+data.id_tugas!!+"/"+data.id_tugas!!+".mp4").delete().addOnSuccessListener {

                                    }

                                    val intent: Intent =
                                        Intent(this@DetailTugasActivity, TugasActivity::class.java)
                                    startActivity(intent)
                                    Toast.makeText(
                                        context,
                                        "Jurnal Berhasil di Hapus",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            setNegativeButton("Tidak") { _, _ -> }
                        }.show()
                    }

                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_hapus_edit)
            popup.show()
        }
        tv_link_detail_tugas.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(data.link)
                )
            )
        }

    }


    //recyclerView hasil tugas
    private fun showRecyclerList(list: ArrayList<HasilTugas>): HasilTugasAdapter {
        val adapter = HasilTugasAdapter(list) {
            //Log.d("Tugas Activity", "Result: $it")

        }

        getPageHasilTugasList { item: ArrayList<HasilTugas> ->
            hasilTugas.addAll(item)
            Log.d("Tugas Activity", "showRecyclerList: before adapter notify")
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            Log.d("Tugas Activity", "showRecyclerList: before rv_tugas set adapter layout")
            rv_hasil_tugas.layoutManager = LinearLayoutManager(this)
            rv_hasil_tugas.adapter = adapter

        }
        return adapter
    }

    private fun getPageHasilTugasList(callback: (item: ArrayList<HasilTugas>) -> Unit) {
        val data = intent.getParcelableExtra<Tugas>("data")
        val listTugas: ArrayList<HasilTugas> = arrayListOf()
        Log.d("Tugas Activity", "getPageTugasList: before get collection data")
        hasilTugasCollection.document(data.id_tugas!!).collection("Isi Tugas").addSnapshotListener { result, e ->
            if (e != null) {
                Log.w("Tugas Activity", "listen:error", e)
                return@addSnapshotListener
            }

            for (document in result!!) {
                Log.d("TugasActivity", document.toString())
                listTugas.add(
                    HasilTugas(
                        document.id,
                        document.getString("avatar"),
                        document.getString("nama"),
                        document.getString("waktu"),
                        document.getString("file")
                    )
                )

            }

            Log.d("TugasActivity", "callback should be call")
            callback.invoke(listTugas)
        }
        Log.d("Tugas Activity", "getPageTugasList: after get collection data, should not be printed")
    }




}