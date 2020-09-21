package com.example.kiddleapp.Pengumuman
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.MediaController
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.Komentar.Komentar
import com.example.kiddleapp.Komentar.KomentarAdapter
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.EditTugasActivity
import com.example.kiddleapp.Tugas.TugasActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import kotlinx.android.synthetic.main.activity_detail_pengumuman.*
import kotlinx.android.synthetic.main.activity_detail_tugas.*

class DetailPengumumanActivity : AppCompatActivity() {

    //untuk menyimpan Hasil TugasActivity
    private var komentar:ArrayList<Komentar> = arrayListOf()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var data: Pengumuman
    var storage = FirebaseStorage.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pengumuman)

        data = intent.getParcelableExtra<Pengumuman>("data")!!
        sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)

        img_back_detail_pengumuman.setOnClickListener {
            onBackPressed()
        }
        // menu
        menu_detail_pengumuman.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    //val intent: Intent = Intent(this@DetailTugasActivity, EditTugasActivity::class.java)
                    val intent: Intent = Intent(
                        this@DetailPengumumanActivity,
                        EditPengumumanActivity::class.java
                    ).putExtra("jenis", "EDIT_PENGUMUMAN")
                    intent.putExtra("data", data)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                else if(it.itemId == R.id.menu_hapus2) {
                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Hapus Pengumuman")
                        setMessage(
                            "Apa anda yakin ingin menghapus pengumuman ini?"
                        )
                        setPositiveButton("Ya") { _, _ ->
                            db.collection("Pengumuman").document(data.id!!).delete()
                                .addOnSuccessListener {
                                    storage.child("Pengumuman/"+data.id!!+"/"+data.id!!+".jpg").delete().addOnSuccessListener {

                                    }
                                    storage.child("Pengumuman/"+data.id!!+"/"+data.id!!+".jpeg").delete().addOnSuccessListener {

                                    }
                                    storage.child("Pengumuman/"+data.id!!+"/"+data.id!!+".png").delete().addOnSuccessListener {

                                    }
                                    storage.child("Pengumuman/"+data.id!!+"/"+data.id!!+".mp4").delete().addOnSuccessListener {

                                    }

                                    val intent: Intent =
                                        Intent(this@DetailPengumumanActivity, PengumumanActivity::class.java)
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


        if (data != null) {
            tv_judul_detail_pengumuman.text = data.judul
            tv_tanggal_detail_pengumuman.text = data.tanggal
            tv_isi_detail_pengumuman.text = data.isi
            if(!data.gambar.isNullOrEmpty()) {
                img_detail_pengumuman.visibility = View.VISIBLE
                vv_detail_pengumuman.visibility = View.GONE
                Glide.with(this).load(data.gambar).centerCrop().into(img_detail_pengumuman)
            } else if(!data.video.isNullOrEmpty()){
                vv_detail_pengumuman.visibility = View.VISIBLE
                img_detail_pengumuman.visibility = View.GONE
                vv_detail_pengumuman.setVideoURI(Uri.parse(data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_detail_pengumuman.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_detail_pengumuman)
                vv_detail_pengumuman.seekTo(10)
            }
        }

        showRecylerList(komentar)

        btn_komentar.setOnClickListener {v ->
            if(!tv_komentar.text.toString().isBlank()) {
                db.document("Komentar/${data.id}/Isi Komentar/${sharedPreferences.getString("id_guru", "")}").set(
                    mapOf("avatar" to sharedPreferences.getString("avatar", ""),
                        "nama" to sharedPreferences.getString("nama", ""),
                        "isi" to tv_komentar.text.toString(),
                        "jabatan" to sharedPreferences.getString("jabatan", "")
                    )
                )
                this.recreate()
            }
            tv_komentar.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun showRecylerList(list: ArrayList<Komentar>): KomentarAdapter {
        val adapter = KomentarAdapter(list) {

        }

        getPageKomentarList {item:ArrayList<Komentar> ->
            komentar.addAll(item)
            adapter.notifyDataSetChanged()
            adapter.addItemToList(list)
            rv_komentar.layoutManager = LinearLayoutManager(this)
            rv_komentar.adapter = adapter
        }

        return adapter
    }

    private fun getPageKomentarList(callback: (item: ArrayList<Komentar>) -> Unit) {
        val listKomentar: ArrayList<Komentar> = arrayListOf()
        db.document("Komentar/${data.id}").collection("Isi Komentar").addSnapshotListener { value, error ->
            if(error != null) return@addSnapshotListener
            for(document in value!!) {
                listKomentar.add(
                    Komentar(
                        document.getString("avatar"),
                        document.getString("nama"),
                        document.getString("jabatan"),
                        document.getString("isi")
                    )
                )
            }
            callback.invoke(listKomentar)
        }
    }
}