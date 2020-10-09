package com.example.kiddleapp.Kegiatan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.TugasActivity
import com.google.common.io.Files.getFileExtension
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_jurnal.*
import kotlinx.android.synthetic.main.activity_edit_kegiatan.*
import kotlinx.android.synthetic.main.activity_edit_tugas.*
import java.util.*

class EditKegiatanActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference
    lateinit var listKegiatan: Kegiatan

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_kegiatan)

        fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val simpleDateFormat2 = SimpleDateFormat("dd-MM-yyyy")
        val tanggal : String = simpleDateFormat2.format(Date())


        if(intent.getStringExtra("jenis") == "EDIT_KEGIATAN") {
            //mengambil data dari halaman sebelumnya
            val data = intent.getParcelableExtra<Kegiatan>("data")
            //kembali

            img_back_edit_kegiatan.setOnClickListener {
                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data",data)
                startActivity(intent)
                finish()
            }

            tv_deskripsi_edit_kegiatan.setText(data.isi)
            tv_judul_edit_kegiatan.setText(data.judul)

            if (data.link != "") {
                input_link_edit_kegiatan.visibility = View.VISIBLE
                tv_link_edit_kegiatan.setText(data.link)
            }

            if (data.gambar != "") {
                image_uri = Uri.parse(data.gambar)
                frame_edit_kegiatan.visibility = View.VISIBLE
                img_edit_kegiatan.visibility = View.VISIBLE
                vv_edit_kegiatan.visibility = View.GONE
                btn_tutup_edit_kegiatan.visibility = View.VISIBLE
                Glide.with(this).load(data.gambar).centerCrop().into(img_edit_kegiatan)
            } else if (data.video != "") {
                image_uri = Uri.parse(data.video)
                frame_edit_kegiatan.visibility = View.VISIBLE
                vv_edit_kegiatan.visibility = View.VISIBLE
                img_edit_kegiatan.visibility = View.GONE
                vv_edit_kegiatan.setVideoURI(Uri.parse(data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_edit_kegiatan.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_kegiatan)
                vv_edit_kegiatan.seekTo(10)
                btn_tutup_edit_kegiatan.visibility = View.VISIBLE

            }

            btn_simpan_edit_kegiatan.setOnClickListener {

                btn_simpan_edit_kegiatan.isEnabled = false
                btn_simpan_edit_kegiatan.text = "Loading"

                if (tv_judul_edit_kegiatan.text.toString()
                        .isEmpty() || tv_deskripsi_edit_kegiatan.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_kegiatan.isEnabled = true
                    btn_simpan_edit_kegiatan.text = "Simpan"

                } else {
                    if (tv_link_edit_kegiatan.text.toString().isValidUrl() || tv_link_edit_kegiatan.text.toString().isNullOrEmpty()) {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(data.id + "." + getFileExtension(image_uri!!))

                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(image_uri!!) == "jpeg") {
                                when (data.jenis) {
                                    "Kegiatan" -> {
                                        storage.child("Kegiatan").child(data.id!!)
                                            .child(builder.toString()).putFile(image_uri!!)
                                            .addOnSuccessListener {
                                                storage.child("Kegiatan").child(data.id!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                        db.collection("Kegiatan").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to it.toString(),
                                                                    "video" to ""

                                                                )
                                                            )

                                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                        data.tanggal, it.toString(),"",tv_link_edit_kegiatan.text.toString())
                                                    }.addOnCompleteListener {
                                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                        startActivity(intent)
                                                        finish()
                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }


                                    }
                                    "Parenting" -> {
                                        storage.child("Parenting").child(data.id!!)
                                            .child(builder.toString()).putFile(image_uri!!)
                                            .addOnSuccessListener {
                                                storage.child("Parenting").child(data.id!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                        db.collection("Parenting").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to it.toString(),
                                                                    "video" to ""

                                                                )
                                                            )
                                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                            data.tanggal, it.toString(),"",tv_link_edit_kegiatan.text.toString())
                                                    }.addOnCompleteListener {
                                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                        startActivity(intent)
                                                        finish()
                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }


                                    }
                                    "Materi" -> {
                                        storage.child("Materi").child(data.id!!)
                                            .child(builder.toString()).putFile(image_uri!!)
                                            .addOnSuccessListener {
                                                storage.child("Materi").child(data.id!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                        db.collection("Materi").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to it.toString(),
                                                                    "video" to ""

                                                                )
                                                            )
                                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                            data.tanggal, it.toString(),"",tv_link_edit_kegiatan.text.toString())

                                                    }.addOnCompleteListener {
                                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                        startActivity(intent)
                                                        finish()

                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }


                                    }
                                }
                            } else if (getFileExtension(image_uri!!) == "mp4") {

                                when (data.jenis) {
                                    "Kegiatan" -> {
                                        storage.child("Kegiatan").child(data.id!!)
                                            .child(builder.toString()).putFile(image_uri!!)
                                            .addOnSuccessListener {
                                                storage.child("Kegiatan").child(data.id!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                        db.collection("Kegiatan").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to "",
                                                                    "video" to it.toString()

                                                                )
                                                            )
                                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                            data.tanggal, "",it.toString(),tv_link_edit_kegiatan.text.toString())

                                                    }.addOnCompleteListener {
                                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                        startActivity(intent)
                                                        finish()

                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }


                                    }
                                    "Parenting" -> {
                                        storage.child("Parenting").child(data.id!!)
                                            .child(builder.toString()).putFile(image_uri!!)
                                            .addOnSuccessListener {
                                                storage.child("Parenting").child(data.id!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                        db.collection("Parenting").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to "",
                                                                    "video" to it.toString()

                                                                )
                                                            )
                                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                            data.tanggal, "",it.toString(),tv_link_edit_kegiatan.text.toString())

                                                    }.addOnCompleteListener {
                                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                        startActivity(intent)
                                                        finish()

                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }


                                    }
                                    "Materi" -> {
                                        storage.child("Materi").child(data.id!!)
                                            .child(builder.toString()).putFile(image_uri!!)
                                            .addOnSuccessListener {
                                                storage.child("Materi").child(data.id!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                        db.collection("Materi").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to "",
                                                                    "video" to it.toString()

                                                                )
                                                            )
                                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                            data.tanggal, "",it.toString(),tv_link_edit_kegiatan.text.toString())

                                                    }.addOnCompleteListener {
                                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                        startActivity(intent)
                                                        finish()

                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }


                                    }
                                }
                            }else{
                                when (data.jenis) {
                                    "Kegiatan" -> {
                                        db.collection("Kegiatan").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to data.gambar,
                                                                    "video" to  data.video

                                                                )
                                                            )
                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                            data.tanggal, data.gambar,data.video,tv_link_edit_kegiatan.text.toString())

                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                        startActivity(intent)
                                        finish()

                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                    "Parenting" -> {
                                                        db.collection("Parenting").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to data.gambar,
                                                                    "video" to data.video

                                                                )
                                                            )

                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                            data.tanggal, data.gambar,data.video,tv_link_edit_kegiatan.text.toString())

                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                        startActivity(intent)
                                        finish()
                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }

                                    "Materi" -> {

                                                        db.collection("Materi").document(data.id!!)
                                                            .update(
                                                                mapOf(
                                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                                    "link" to tv_link_edit_kegiatan.text.toString(),
                                                                    "gambar" to data.gambar,
                                                                    "video" to data.video

                                                                )
                                                            )

                                        listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                            data.tanggal, data.gambar,data.video,tv_link_edit_kegiatan.text.toString())

                                        val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                        startActivity(intent)
                                        finish()
                                                        Toast.makeText(
                                                            this,
                                                            "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }



                            }

                        } else if (image_uri == null) {
                            when (data.jenis) {
                                "Kegiatan" -> {
                                    db.collection("Kegiatan").document(data.id!!)
                                        .update(mapOf(
                                            "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                            "judul" to tv_judul_edit_kegiatan.text.toString(),
                                            "link" to tv_link_edit_kegiatan.text.toString(),
                                            "gambar" to "",
                                            "video" to ""
                                        )
                                        ).addOnCompleteListener {
                                            listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                data.tanggal, "","",tv_link_edit_kegiatan.text.toString())

                                            val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(
                                                this,
                                                "Simpan Berhasil",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                }
                                "Parenting" -> {
                                    db.collection("Parenting").document(data.id!!)
                                        .update(mapOf(
                                            "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                            "judul" to tv_judul_edit_kegiatan.text.toString(),
                                            "link" to tv_link_edit_kegiatan.text.toString(),
                                            "gambar" to "",
                                            "video" to ""
                                        )
                                        ).addOnCompleteListener {

                                            listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                data.tanggal, "","",tv_link_edit_kegiatan.text.toString())

                                            val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(
                                                this,
                                                "Simpan Berhasil",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }


                                }
                                "Materi" -> {
                                    db.collection("Materi").document(data.id!!)
                                        .update(mapOf(
                                            "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                            "judul" to tv_judul_edit_kegiatan.text.toString(),
                                            "link" to tv_link_edit_kegiatan.text.toString(),
                                            "gambar" to "",
                                            "video" to ""
                                        )
                                        ).addOnCompleteListener {

                                            listKegiatan=Kegiatan(data.id,data.jenis,tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                data.tanggal, "","",tv_link_edit_kegiatan.text.toString())

                                            val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(
                                                this,
                                                "Simpan Berhasil",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }


                                }
                            }
                        }

                    } else {
                        tv_link_edit_kegiatan.error = "Pastikan tautan sudah benar"
                        btn_simpan_edit_kegiatan.isEnabled = true
                        btn_simpan_edit_kegiatan.text = "Simpan"

                    }
                }
            }



        }else if (intent.getStringExtra("jenis") == "TAMBAH_KEGIATAN") {
            img_back_edit_kegiatan.setOnClickListener {
                val intent: Intent = Intent(
                    this@EditKegiatanActivity,
                    KegiatanActivity::class.java
                ).putExtra("jenis", "Kegiatan")

                startActivity(intent)
                finish()
            }
            // simpan
            //link
            btn_simpan_edit_kegiatan.setOnClickListener {
                btn_simpan_edit_kegiatan.isEnabled = false
                btn_simpan_edit_kegiatan.text = "Loading"

                if (tv_judul_edit_kegiatan.text.toString().isEmpty() || tv_deskripsi_edit_kegiatan.text.toString().isEmpty()) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_kegiatan.isEnabled = true
                    btn_simpan_edit_kegiatan.text = "Simpan"


                } else {
                    if (tv_link_edit_kegiatan.text.toString().isValidUrl() || tv_link_edit_kegiatan.text.toString().isNullOrEmpty()) {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(currentDateAndTime + "." + getFileExtension(image_uri!!))
                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(
                                    image_uri!!
                                ) == "jpeg"
                            ) {
                                storage.child("Kegiatan").child(currentDateAndTime)
                                    .child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Kegiatan").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val kegiatan = hashMapOf(
                                                    "id" to currentDateAndTime,
                                                    "jenis" to "Kegiatan",
                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                    "tanggal" to tanggal,
                                                    "gambar" to it.toString(),
                                                    "video" to "",
                                                    "link" to tv_link_edit_kegiatan.text.toString()
                                                )

                                                listKegiatan=Kegiatan(currentDateAndTime,"Kegiatan",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                    tanggal, it.toString(),"",tv_link_edit_kegiatan.text.toString())

                                                db.collection("Kegiatan").document(currentDateAndTime).set(kegiatan)
                                            }.addOnCompleteListener {

                                                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                    }

                            } else if (getFileExtension(image_uri!!) == "mp4") {
                                storage.child("Kegiatan").child(currentDateAndTime)
                                    .child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Kegiatan").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val kegiatan = hashMapOf(
                                                    "id" to currentDateAndTime,
                                                    "jenis" to "Kegiatan",
                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                    "tanggal" to tanggal,
                                                    "gambar" to "",
                                                    "video" to it.toString(),
                                                    "link" to tv_link_edit_kegiatan.text.toString()
                                                )

                                                listKegiatan=Kegiatan(currentDateAndTime,"Kegiatan",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                    tanggal, "",it.toString(),tv_link_edit_kegiatan.text.toString())

                                                db.collection("Kegiatan").document(currentDateAndTime)
                                                    .set(kegiatan)
                                            }.addOnCompleteListener {
                                                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                    }
                            }
                        }
                        else {
                            val kegiatan = hashMapOf(
                                "id" to currentDateAndTime,
                                "jenis" to "Kegiatan",
                                "judul" to tv_judul_edit_kegiatan.text.toString(),
                                "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                "tanggal" to tanggal,
                                "gambar" to "",
                                "video" to "",
                                "link" to tv_link_edit_kegiatan.text.toString()
                            )
                            listKegiatan=Kegiatan(currentDateAndTime,"Kegiatan",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                tanggal, "","",tv_link_edit_kegiatan.text.toString())

                            db.collection("Kegiatan").document(currentDateAndTime).set(kegiatan)
                                .addOnCompleteListener {
                                    val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                    startActivity(intent)
                                    finish()
                                    Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        }
                    }else {
                        tv_link_edit_kegiatan.error = "Pastikan tautan sudah benar"
                        btn_simpan_edit_kegiatan.isEnabled = true
                        btn_simpan_edit_kegiatan.text = "Simpan"

                    }

                }
            }
        }else if (intent.getStringExtra("jenis") == "TAMBAH_PARENTING") {


            img_back_edit_kegiatan.setOnClickListener {
                val intent: Intent = Intent(
                    this@EditKegiatanActivity,
                    KegiatanActivity::class.java
                ).putExtra("jenis", "Parenting")

                startActivity(intent)
                finish()
            }
            // simpan
            //link
            btn_simpan_edit_kegiatan.setOnClickListener {
                btn_simpan_edit_kegiatan.isEnabled = false
                btn_simpan_edit_kegiatan.text = "Loading"

                if (tv_judul_edit_kegiatan.text.toString()
                        .isEmpty() || tv_deskripsi_edit_kegiatan.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_kegiatan.isEnabled = true
                    btn_simpan_edit_kegiatan.text = "Simpan"


                } else {
                    if (tv_link_edit_kegiatan.text.toString().isValidUrl() || tv_link_edit_kegiatan.text.toString().isNullOrEmpty()) {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(currentDateAndTime + "." + getFileExtension(image_uri!!))
                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(
                                    image_uri!!
                                ) == "jpeg"
                            ) {
                                storage.child("Parenting").child(currentDateAndTime)
                                    .child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Parenting").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val kegiatan = hashMapOf(
                                                    "id" to currentDateAndTime,
                                                    "jenis" to "Parenting",
                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                    "tanggal" to tanggal,
                                                    "gambar" to it.toString(),
                                                    "video" to "",
                                                    "link" to tv_link_edit_kegiatan.text.toString()
                                                )
                                                listKegiatan=Kegiatan(currentDateAndTime,"Parenting",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                    tanggal, it.toString(),"",tv_link_edit_kegiatan.text.toString())

                                                db.collection("Parenting").document(currentDateAndTime)
                                                    .set(kegiatan)
                                            }.addOnCompleteListener {
                                                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                    }

                            } else if (getFileExtension(image_uri!!) == "mp4") {
                                storage.child("Parenting").child(currentDateAndTime)
                                    .child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Parenting").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val kegiatan = hashMapOf(
                                                    "id" to currentDateAndTime,
                                                    "jenis" to "Parenting",
                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                    "tanggal" to tanggal,
                                                    "gambar" to "",
                                                    "video" to it.toString(),
                                                    "link" to tv_link_edit_kegiatan.text.toString()
                                                )
                                                listKegiatan=Kegiatan(currentDateAndTime,"Parenting",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                    tanggal, "",it.toString(),tv_link_edit_kegiatan.text.toString())

                                                db.collection("Parenting").document(currentDateAndTime)
                                                    .set(kegiatan)
                                            }.addOnCompleteListener {
                                                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                    }
                            }
                        } else {
                            val kegiatan = hashMapOf(
                                "id" to currentDateAndTime,
                                "jenis" to "Parenting",
                                "judul" to tv_judul_edit_kegiatan.text.toString(),
                                "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                "tanggal" to tanggal,
                                "gambar" to "",
                                "video" to "",
                                "link" to tv_link_edit_kegiatan.text.toString()
                            )
                            listKegiatan=Kegiatan(currentDateAndTime,"Parenting",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                tanggal, "","",tv_link_edit_kegiatan.text.toString())

                            db.collection("Parenting").document(currentDateAndTime).set(kegiatan)
                                .addOnCompleteListener {
                                    val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                    startActivity(intent)
                                    finish()
                                    Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        }
                    }else{
                        tv_link_edit_kegiatan.error = "Pastikan tautan sudah benar"
                        btn_simpan_edit_kegiatan.isEnabled = true
                        btn_simpan_edit_kegiatan.text = "Simpan"
                    }

                    }

            }
        }else if (intent.getStringExtra("jenis") == "TAMBAH_MATERI") {
            img_back_edit_kegiatan.setOnClickListener {
                val intent: Intent = Intent(
                    this@EditKegiatanActivity,
                    KegiatanActivity::class.java
                ).putExtra("jenis", "Materi")

                startActivity(intent)
                finish()
            }
            // simpan
            //link
            btn_simpan_edit_kegiatan.setOnClickListener {
                btn_simpan_edit_kegiatan.isEnabled = false
                btn_simpan_edit_kegiatan.text = "Loading"

                if (tv_judul_edit_kegiatan.text.toString().isEmpty() || tv_deskripsi_edit_kegiatan.text.toString().isEmpty()) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_kegiatan.isEnabled = true
                    btn_simpan_edit_kegiatan.text = "Simpan"


                } else {
                    if (tv_link_edit_kegiatan.text.toString()
                            .isValidUrl() || tv_link_edit_kegiatan.text.toString().isNullOrEmpty()
                    ) {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(currentDateAndTime + "." + getFileExtension(image_uri!!))
                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(
                                    image_uri!!
                                ) == "jpeg"
                            ) {
                                storage.child("Materi").child(currentDateAndTime)
                                    .child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Materi").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val kegiatan = hashMapOf(
                                                    "id" to currentDateAndTime,
                                                    "jenis" to "Materi",
                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                    "tanggal" to tanggal,
                                                    "gambar" to it.toString(),
                                                    "video" to "",
                                                    "link" to tv_link_edit_kegiatan.text.toString()
                                                )
                                                listKegiatan=Kegiatan(currentDateAndTime,"Materi",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                    tanggal, it.toString(),"",tv_link_edit_kegiatan.text.toString())

                                                db.collection("Materi").document(currentDateAndTime)
                                                    .set(kegiatan)
                                            }.addOnCompleteListener {
                                                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                    }

                            } else if (getFileExtension(image_uri!!) == "mp4") {
                                storage.child("Materi").child(currentDateAndTime)
                                    .child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Materi").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val kegiatan = hashMapOf(
                                                    "id" to currentDateAndTime,
                                                    "jenis" to "Materi",
                                                    "judul" to tv_judul_edit_kegiatan.text.toString(),
                                                    "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                                    "tanggal" to tanggal,
                                                    "gambar" to "",
                                                    "video" to it.toString(),
                                                    "link" to tv_link_edit_kegiatan.text.toString()
                                                )
                                                listKegiatan=Kegiatan(currentDateAndTime,"Materi",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                                    tanggal, "",it.toString(),tv_link_edit_kegiatan.text.toString())

                                                db.collection("Materi").document(currentDateAndTime)
                                                    .set(kegiatan)
                                            }.addOnCompleteListener {
                                                val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                    }
                            }
                        }else {
                            val kegiatan = hashMapOf(
                                "id" to currentDateAndTime,
                                "jenis" to "Materi",
                                "judul" to tv_judul_edit_kegiatan.text.toString(),
                                "isi" to tv_deskripsi_edit_kegiatan.text.toString(),
                                "tanggal" to tanggal,
                                "gambar" to "",
                                "video" to "",
                                "link" to tv_link_edit_kegiatan.text.toString()
                            )
                            listKegiatan=Kegiatan(currentDateAndTime,"Materi",tv_judul_edit_kegiatan.text.toString(),tv_deskripsi_edit_kegiatan.text.toString(),
                                tanggal, "","",tv_link_edit_kegiatan.text.toString())

                            db.collection("Materi").document(currentDateAndTime).set(kegiatan)
                                .addOnCompleteListener {
                                    val intent: Intent = Intent(this@EditKegiatanActivity, DetailKegiatanActivity::class.java).putExtra("data", listKegiatan)
                                    startActivity(intent)
                                    finish()
                                    Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        }
                    } else{
                        tv_link_edit_kegiatan.error = "Pastikan tautan sudah benar"
                        btn_simpan_edit_kegiatan.isEnabled = true
                        btn_simpan_edit_kegiatan.text = "Simpan"

                }

                    }
            }
        }


        //tutup gambar
       // hapus video belum bisa
        btn_tutup_edit_kegiatan.setOnClickListener {
            image_uri=null
            frame_edit_kegiatan.visibility = View.GONE
            img_edit_kegiatan.setImageResource(0)
            img_edit_kegiatan.visibility = View.GONE
            vv_edit_kegiatan.setVideoURI(Uri.parse(""))
            vv_edit_kegiatan.visibility = View.GONE
            btn_tutup_edit_kegiatan.visibility = View.GONE
        }



        //galeri
        img_gambar_edit_kegiatan.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/* video/*"
            startActivityForResult(intent, 1)
        }

        //link
        img_link_edit_kegiatan.setOnClickListener {
            input_link_edit_kegiatan.visibility = View.VISIBLE
        }

        //kamera
        img_kamera_edit_kegiatan.setOnClickListener {
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system os is < marshmallow
                openCamera()
            }
        }


    }

    //kamera
    private fun getFileExtension(imgLocation: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgLocation))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //ambil gambar atau video galeri
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            if (data?.data.toString().contains("image")) {
                image_uri = data?.data
                frame_edit_kegiatan.visibility = View.VISIBLE
                img_edit_kegiatan.visibility = View.VISIBLE
                vv_edit_kegiatan.visibility = View.GONE
               // img_edit_kegiatan.setImageURI(data?.data)
                Glide.with(this).load(image_uri).centerCrop().into(img_edit_kegiatan)

            } else if (data?.data.toString().contains("video")) {
                image_uri = data?.data
                frame_edit_kegiatan.visibility = View.VISIBLE
                vv_edit_kegiatan.visibility = View.VISIBLE
                img_edit_kegiatan.visibility = View.GONE
                vv_edit_kegiatan.setVideoURI(data?.data)
                var media_Controller: MediaController = MediaController(this)
                vv_edit_kegiatan.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_kegiatan)
                vv_edit_kegiatan.seekTo(10)
            }
        }

        //ambil gambar kamera
        else if (resultCode == Activity.RESULT_OK) {
            //set image captured to image view
            frame_edit_kegiatan.visibility = View.VISIBLE
            img_edit_kegiatan.visibility = View.VISIBLE
            vv_edit_kegiatan.visibility = View.GONE
          //  img_edit_kegiatan.setImageURI(image_uri)
            Glide.with(this).load(image_uri).centerCrop().into(img_edit_kegiatan)
        }

        btn_tutup_edit_kegiatan.visibility = View.VISIBLE
    }

    //kamera
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        //camera intent
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }


        //link


    }
}