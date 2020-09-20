package com.example.kiddleapp.Jurnal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.MediaController
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.TugasActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail_jurnal.*
import kotlinx.android.synthetic.main.activity_detail_tugas.*


class DetailJurnalActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jurnal)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Jurnal>("data")

        tv_jenis_detail_jurnal.text = data.jenis
        tv_kelas_detail_jurnal.text = data.kelas
        tv_judul_detail_jurnal.text = data.judul
        tv_tanggal_detail_jurnal.text=data.tanggal
        tv_isi_detail_jurnal.text=data.isi

        if(data.gambar!="") {
            img_detail_jurnal.visibility = View.VISIBLE
            vv_detail_jurnal.visibility = View.GONE
            Glide.with(this).load(data.gambar).centerCrop().into(img_detail_jurnal)
           // img_detail_jurnal.setImageResource(data.gambar)
        }
        else if(data.video!="") {
            vv_detail_jurnal.visibility = View.VISIBLE
            img_detail_jurnal.visibility = View.GONE
            vv_detail_jurnal.setVideoURI(Uri.parse( data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_jurnal.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_jurnal)
            vv_detail_jurnal.seekTo(10)

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_jurnal.setOnClickListener {
            val intent: Intent =  Intent(this@DetailJurnalActivity,JurnalActivity::class.java  )
            startActivity(intent)
        }

        // menu
        menu_detail_jurnal.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    val intent: Intent = Intent(
                        this@DetailJurnalActivity,
                        EditJurnalActivity::class.java
                    ).putExtra("data", data)
                    intent.putExtra("jenis","EDIT_JURNAL")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Hapus Jurnal")
                        setMessage(
                            "Apa anda yakin ingin menghapus jurnal ini?"
                        )
                        setPositiveButton("Ya") { _, _ ->
                            db.collection("Jurnal").document(data.id_jurnal!!).delete()
                        .addOnSuccessListener {
                            storage.child("Jurnal/"+data.id_jurnal!!+"/"+data.id_jurnal!!+".jpg").delete().addOnSuccessListener {

                            }
                            storage.child("Jurnal/"+data.id_jurnal!!+"/"+data.id_jurnal!!+".jpeg").delete().addOnSuccessListener {

                            }
                            storage.child("Jurnal/"+data.id_jurnal!!+"/"+data.id_jurnal!!+".png").delete().addOnSuccessListener {

                            }
                            storage.child("Jurnal/"+data.id_jurnal!!+"/"+data.id_jurnal!!+".mp4").delete().addOnSuccessListener {

                            }

                            val intent: Intent =
                                Intent(this@DetailJurnalActivity, JurnalActivity::class.java)
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
    }
}