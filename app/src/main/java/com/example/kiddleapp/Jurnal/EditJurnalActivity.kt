package com.example.kiddleapp.Jurnal

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
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Model.Tugas
import com.example.kiddleapp.Tugas.TugasActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_jurnal.*
import kotlinx.android.synthetic.main.activity_edit_tugas.*
import java.util.*


class EditJurnalActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_jurnal)
        val data = intent.getParcelableExtra<Jurnal>("data")

        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val simpleDateFormat2 = SimpleDateFormat("dd-MM-yyyy")
        val tanggal : String = simpleDateFormat2.format(Date())

        if(intent.getStringExtra("jenis") == "EDIT_JURNAL") {
            //mengambil data dari halaman sebelumnya
            val data = intent.getParcelableExtra<Jurnal>("data")
            tv_kelas_edit_jurnal.setText(data.kelas)
            tv_deskripsi_edit_jurnal.setText(data.isi)
            tv_aspek_edit_jurnal.setText(data.jenis)
            tv_judul_edit_jurnal.setText(data.judul)


            if (data.gambar != "") {
                frame_edit_jurnal.visibility = View.VISIBLE
                img_edit_jurnal.visibility = View.VISIBLE
                vv_edit_jurnal.visibility = View.GONE
                btn_tutup_edit_jurnal.visibility = View.VISIBLE
                Glide.with(this).load(data.gambar).centerCrop().into(img_edit_jurnal)

            } else if (data.video != "") {
                frame_edit_jurnal.visibility = View.VISIBLE
                vv_edit_jurnal.visibility = View.VISIBLE
                img_edit_jurnal.visibility = View.GONE
            //    vv_edit_jurnal.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_edit_jurnal.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_jurnal)
                vv_edit_jurnal.seekTo(10)
                btn_tutup_edit_jurnal.visibility = View.VISIBLE
            }

                btn_simpan_edit_jurnal.setOnClickListener {

                    btn_simpan_edit_jurnal.isEnabled = false
                    btn_simpan_edit_jurnal.text = "Loading"

                    if(tv_kelas_edit_jurnal.text.toString().isEmpty() || tv_aspek_edit_jurnal.text.toString().isEmpty() ||tv_judul_edit_jurnal.text.toString().isEmpty() ||tv_deskripsi_edit_jurnal.text.toString().isEmpty()) {
                        Toast.makeText(
                            this,
                            "Semua Kolom Harus Diisi!",
                            Toast.LENGTH_SHORT
                        ).show()
                        btn_simpan_edit_jurnal.isEnabled = true
                        btn_simpan_edit_jurnal.text = "Simpan"


                    }else {

                            if (image_uri != null) {
                                val builder = StringBuilder()
                                builder.append(data.id_jurnal + "." + getFileExtension(image_uri!! ))

                                if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension( image_uri!!) == "jpeg") {
                                    storage.child("Jurnal").child(data.id_jurnal!!).child(builder.toString()).putFile(image_uri!!).addOnSuccessListener {
                                        storage.child("Jurnal").child(data.id_jurnal!!).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                            db.collection("Jurnal").document(data.id_jurnal!!)
                                                .update(mapOf(
                                                    "id_jurnal" to  currentDateAndTime,
                                                    "jenis" to  tv_aspek_edit_jurnal.text.toString(),
                                                    "kelas" to tv_kelas_edit_jurnal.text.toString(),
                                                    "judul" to  tv_judul_edit_jurnal.text.toString(),
                                                    "isi" to  tv_deskripsi_edit_jurnal.text.toString(),
                                                    "tanggal" to  tanggal,
                                                    "gambar" to   it.toString(),
                                                    "video" to   ""
                                                )
                                                )}.addOnCompleteListener {
                                            val intent: Intent =  Intent(this@EditJurnalActivity,JurnalActivity::class.java  )
                                            startActivity(intent)
                                            Toast.makeText(
                                                this,
                                                "Simpan Berhasil",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                                } else if (getFileExtension(image_uri!!) == "mp4" ) {
                                    //storage = FirebaseStorage.getInstance().reference.child("Tugas").child(sharedPreferences.getString("id_tugas", "").toString()).child(builder.toString())

                                    storage.child("Jurnal").child(data.id_jurnal!!)
                                        .child(builder.toString()).putFile(image_uri!!)
                                        .addOnSuccessListener {
                                            storage.child("Jurnal").child(data.id_jurnal!!)
                                                .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                db.collection("Jurnal").document(data.id_jurnal!!)
                                                    .update(
                                                        mapOf(
                                                            "id_jurnal" to currentDateAndTime,
                                                            "jenis" to tv_aspek_edit_jurnal.text.toString(),
                                                            "kelas" to tv_kelas_edit_jurnal.text.toString(),
                                                            "judul" to tv_judul_edit_jurnal.text.toString(),
                                                            "isi" to tv_deskripsi_edit_jurnal.text.toString(),
                                                            "tanggal" to tanggal,
                                                            "gambar" to "",
                                                            "video" to it.toString()
                                                        )
                                                    )
                                            }.addOnCompleteListener {
                                                val intent: Intent = Intent(
                                                    this@EditJurnalActivity,
                                                    JurnalActivity::class.java
                                                )
                                                startActivity(intent)
                                                Toast.makeText(
                                                    this,
                                                    "Simpan Berhasil",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                        }
                                }
                            }else if (image_uri == null){
                                db.collection("Jurnal").document(data.id_jurnal!!)
                                    .update(mapOf(
                                        "id_jurnal" to  currentDateAndTime,
                                        "jenis" to  tv_aspek_edit_jurnal.text.toString(),
                                        "kelas" to tv_kelas_edit_jurnal.text.toString(),
                                        "judul" to  tv_judul_edit_jurnal.text.toString(),
                                        "isi" to  tv_deskripsi_edit_jurnal.text.toString(),
                                        "tanggal" to  tanggal,
                                        "gambar" to   "",
                                        "video" to   ""
                                    )
                                    ).addOnCompleteListener {

                                        val intent: Intent =  Intent(this@EditJurnalActivity,JurnalActivity::class.java  )
                                        startActivity(intent)
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
        }else if (intent.getStringExtra("jenis") == "TAMBAH_JURNAL") {
            // simpan
            //link
            btn_simpan_edit_jurnal.setOnClickListener {
                btn_simpan_edit_jurnal.isEnabled = false
                btn_simpan_edit_jurnal.text = "Loading"

                if(tv_kelas_edit_jurnal.text.toString().isEmpty() || tv_aspek_edit_jurnal.text.toString().isEmpty() ||tv_judul_edit_jurnal.text.toString().isEmpty() ||tv_deskripsi_edit_jurnal.text.toString().isEmpty()) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_jurnal.isEnabled = true
                    btn_simpan_edit_jurnal.text = "Simpan"

                }else {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(currentDateAndTime + "." + getFileExtension(image_uri!! ))
                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(image_uri!!) == "jpeg") {
                                storage.child("Jurnal").child(currentDateAndTime).child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Jurnal").child(currentDateAndTime).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                            val jurnal = hashMapOf(
                                                "id_jurnal" to  currentDateAndTime,
                                                "jenis" to  tv_aspek_edit_jurnal.text.toString(),
                                                "kelas" to tv_kelas_edit_jurnal.text.toString(),
                                                "judul" to  tv_judul_edit_jurnal.text.toString(),
                                                "isi" to  tv_deskripsi_edit_jurnal.text.toString(),
                                                "tanggal" to  tanggal,
                                                "gambar" to   it.toString(),
                                                "video" to   ""
                                            )
                                            db.collection("Jurnal").document(currentDateAndTime).set(jurnal)}.addOnCompleteListener {
                                            val intent: Intent =  Intent(this@EditJurnalActivity,
                                                JurnalActivity::class.java  )
                                            startActivity(intent)
                                            Toast.makeText(  this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                                        }

                                    }
                            } else if (getFileExtension(image_uri!!) == "mp4") {
                                storage.child("Jurnal").child(currentDateAndTime).child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Jurnal").child(currentDateAndTime).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                            val jurnal = hashMapOf(
                                                "id_jurnal" to  currentDateAndTime,
                                                "jenis" to  tv_aspek_edit_jurnal.text.toString(),
                                                "kelas" to tv_kelas_edit_jurnal.text.toString(),
                                                "judul" to  tv_judul_edit_jurnal.text.toString(),
                                                "isi" to  tv_deskripsi_edit_jurnal.text.toString(),
                                                "tanggal" to  tanggal,
                                                "gambar" to "",
                                                "video" to it.toString()
                                            )
                                            db.collection("Jurnal").document(currentDateAndTime).set(jurnal)}.addOnCompleteListener {
                                            val intent: Intent =  Intent(this@EditJurnalActivity,
                                                JurnalActivity::class.java  )
                                            startActivity(intent)
                                            Toast.makeText(  this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                                        }

                                    }
                            }
                        } else {
                            val jurnal = hashMapOf(
                                "id_jurnal" to currentDateAndTime,
                                "jenis" to tv_aspek_edit_jurnal.text.toString(),
                                "kelas" to tv_kelas_edit_jurnal.text.toString(),
                                "judul" to tv_judul_edit_jurnal.text.toString(),
                                "isi" to tv_deskripsi_edit_jurnal.text.toString(),
                                "tanggal" to tanggal,
                                "gambar" to "",
                                "video" to ""
                            )
                            db.collection("Jurnal").document(currentDateAndTime).set(jurnal)
                                .addOnCompleteListener {
                                    val intent: Intent = Intent(
                                        this@EditJurnalActivity,
                                        JurnalActivity::class.java
                                    )
                                    startActivity(intent)
                                    Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        }

                }
            }
        }





        //tutup gambar
        // hapus video belum bisa
        btn_tutup_edit_jurnal.setOnClickListener {
            image_uri=null
            frame_edit_jurnal.visibility = View.GONE
            img_edit_jurnal.setImageResource(0)
            img_edit_jurnal.visibility = View.GONE
            vv_edit_jurnal.setVideoURI(Uri.parse(""))
            vv_edit_jurnal.visibility = View.GONE
            btn_tutup_edit_jurnal.visibility = View.GONE

        }

        //kembali
        img_back_edit_jurnal.setOnClickListener {
            val intent: Intent = Intent(this@EditJurnalActivity, JurnalActivity::class.java)
            startActivity(intent)
        }


        // simpan


        //untuk kelas
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_text, items
        )
        (dropdown_kelas_edit_jurnal.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        //untuk aspek
        val items2 = listOf("Keterampilan", "Motorik", "Kognitif", "Agama", "Berbahasa")
        val adapter2 = ArrayAdapter(
            this,
            R.layout.dropdown_text, items2
        )
        (dropdown_edit_jurnal_aspek.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        //galeri
        img_gambar_edit_jurnal.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/* video/*"
            startActivityForResult(intent, 1)
        }

        //kamera
        img_kamera_edit_jurnal.setOnClickListener {
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
                frame_edit_jurnal.visibility = View.VISIBLE
                img_edit_jurnal.visibility = View.VISIBLE
                vv_edit_jurnal.visibility = View.GONE
            //    img_edit_jurnal.setImageURI(image_uri)
                Glide.with(this).load(image_uri).centerCrop().into(img_edit_jurnal)


            } else if (data?.data.toString().contains("video")) {
                image_uri = data?.data
                frame_edit_jurnal.visibility = View.VISIBLE
                vv_edit_jurnal.visibility = View.VISIBLE
                img_edit_jurnal.visibility = View.GONE
                vv_edit_jurnal.setVideoURI(image_uri)
                var media_Controller: MediaController = MediaController(this)
                vv_edit_jurnal.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_jurnal)
                vv_edit_jurnal.seekTo(10)
            }
        }

        //ambil gambar kamera
        else if (resultCode == Activity.RESULT_OK) {
            //set image captured to image view
            frame_edit_jurnal.visibility = View.VISIBLE
            img_edit_jurnal.visibility = View.VISIBLE
            vv_edit_jurnal.visibility = View.GONE
            Glide.with(this).load(image_uri).centerCrop().into(img_edit_jurnal)
          //  img_edit_jurnal.setImageURI(image_uri)
        }

        btn_tutup_edit_jurnal.visibility = View.VISIBLE
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


    }
}