package com.example.kiddleapp.Kegiatan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail_jurnal.*
import kotlinx.android.synthetic.main.activity_detail_kegiatan.*


class DetailKegiatanActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kegiatan)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Kegiatan>("data")
        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)

        tv_judul_detail_kegiatan.text = data.judul
        tv_tanggal_detail_kegiatan.text = data.tanggal
        tv_isi_detail_kegiatan.text=data.isi
        tv_link_detail_kegiatan.text=data.link

        if(data.gambar!="") {
            img_detail_kegiatan.visibility = View.VISIBLE
            vv_detail_kegiatan.visibility = View.GONE
            Glide.with(this).load(data.gambar).centerCrop().into(img_detail_kegiatan)
         //   img_detail_kegiatan.setImageResource(data.gambar)
        }
        else if(data.video!="") {
            vv_detail_kegiatan.visibility = View.VISIBLE
            img_detail_kegiatan.visibility = View.GONE
            vv_detail_kegiatan.setVideoURI(Uri.parse( data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_kegiatan.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_kegiatan)
            vv_detail_kegiatan.seekTo(10)

        }

        if(data.link!="") {
            img_link_detail_kegiatan.visibility = View.VISIBLE
            tv_link_detail_kegiatan.visibility = View.VISIBLE

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_kegiatan.setOnClickListener {
            val intent: Intent = Intent(this@DetailKegiatanActivity, KegiatanActivity::class.java).putExtra("jenis",data.jenis)
            startActivity(intent)
        }


        if(sharedPreferences.getString("kelas","") == "admin") {
            menu_detail_kegiatan.visibility=View.VISIBLE
            // menu
            menu_detail_kegiatan.setOnClickListener {
                val popup: PopupMenu = PopupMenu(this, it)
                popup.setOnMenuItemClickListener {
                    if (it.itemId == R.id.menu_edit2) {
                        val intent: Intent = Intent(
                            this@DetailKegiatanActivity,
                            EditKegiatanActivity::class.java
                        ).putExtra("data", data)
                        intent.putExtra("jenis", "EDIT_KEGIATAN")
                        startActivity(intent)
                        finish()
                        return@setOnMenuItemClickListener true
                    } else if (it.itemId == R.id.menu_hapus2) {
                        MaterialAlertDialogBuilder(this).apply {
                            if (data.jenis == "Kegiatan") {
                                setTitle("Hapus Kegiatan")
                                setMessage(
                                    "Apa anda yakin ingin menghapus kegiatan ini?"
                                )
                                setPositiveButton("Ya") { _, _ ->

                                    db.collection("Kegiatan").document(data.id!!).delete()
                                        .addOnSuccessListener {
                                            storage.child("Kegiatan/" + data.id!! + "/" + data.id!! + ".jpg")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Kegiatan/" + data.id!! + "/" + data.id!! + ".jpeg")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Kegiatan/" + data.id!! + "/" + data.id!! + ".png")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Kegiatan/" + data.id!! + "/" + data.id!! + ".mp4")
                                                .delete().addOnSuccessListener {

                                                }

                                            val intent: Intent = Intent(
                                                this@DetailKegiatanActivity,
                                                KegiatanActivity::class.java
                                            ).putExtra("jenis", data.jenis)
                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(
                                                context,
                                                "Kegiatan Berhasil di Hapus",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    setNegativeButton("Tidak") { _, _ -> }
                                }.show()
                            } else if (data.jenis == "Parenting") {
                                setTitle("Hapus Parenting")
                                setMessage(
                                    "Apa anda yakin ingin menghapus parenting ini?"
                                )
                                setPositiveButton("Ya") { _, _ ->

                                    db.collection("Parenting").document(data.id!!).delete()
                                        .addOnSuccessListener {
                                            storage.child("Parenting/" + data.id!! + "/" + data.id!! + ".jpg")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Parenting/" + data.id!! + "/" + data.id!! + ".jpeg")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Parenting/" + data.id!! + "/" + data.id!! + ".png")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Parenting/" + data.id!! + "/" + data.id!! + ".mp4")
                                                .delete().addOnSuccessListener {

                                                }

                                            val intent: Intent = Intent(
                                                this@DetailKegiatanActivity,
                                                KegiatanActivity::class.java
                                            ).putExtra("jenis", data.jenis)

                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(
                                                context,
                                                "Parenting Berhasil di Hapus",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    setNegativeButton("Tidak") { _, _ -> }
                                }.show()
                            } else if (data.jenis == "Materi") {
                                setTitle("Hapus Materi")
                                setMessage(
                                    "Apa anda yakin ingin menghapus Materi ini?"
                                )
                                setPositiveButton("Ya") { _, _ ->

                                    db.collection("Materi").document(data.id!!).delete()
                                        .addOnSuccessListener {
                                            storage.child("Materi/" + data.id!! + "/" + data.id!! + ".jpg")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Materi/" + data.id!! + "/" + data.id!! + ".jpeg")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Materi/" + data.id!! + "/" + data.id!! + ".png")
                                                .delete().addOnSuccessListener {

                                                }
                                            storage.child("Materi/" + data.id!! + "/" + data.id!! + ".mp4")
                                                .delete().addOnSuccessListener {

                                                }

                                            val intent: Intent = Intent(
                                                this@DetailKegiatanActivity,
                                                KegiatanActivity::class.java
                                            ).putExtra("jenis", data.jenis)

                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(
                                                context,
                                                "Materi Berhasil di Hapus",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    setNegativeButton("Tidak") { _, _ -> }
                                }.show()
                            }

                        }
                    }
                    return@setOnMenuItemClickListener false
                }
                popup.inflate(R.menu.menu_hapus_edit)
                popup.show()
            }
        }


        tv_link_detail_kegiatan.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(data.link)
                )
            )
        }
    }
}