package com.example.kiddleapp.Tugas

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Model.Tugas
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_jurnal.*
import kotlinx.android.synthetic.main.activity_edit_tugas.*
import java.security.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



class EditTugasActivity : AppCompatActivity() {


    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var image_uri: Uri? = null

    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tugas)

        fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

        val data = intent.getParcelableExtra<Tugas>("data")
        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        if (intent.getStringExtra("jenis") == "EDIT_TUGAS") {
            tv_kelas_edit_tugas.setText(data.kelas)
            tv_deskripsi_edit_tugas.setText(data.isi)
            tv_aspek_edit_tugas.setText(data.judul)
            tv_tanggal_edit_tugas.setText(data.tanggal)
            tv_jam_edit_tugas.setText(data.jam)

            if (data.link != "") {
                input_link_edit_tugas.visibility = View.VISIBLE
                tv_link_edit_tugas.setText(data.link)
            }

            if (data.gambar != "") {
                frame_edit_tugas.visibility = View.VISIBLE
                img_edit_tugas.visibility = View.VISIBLE
                vv_edit_tugas.visibility = View.GONE
                btn_tutup_edit_tugas.visibility = View.VISIBLE
                Glide.with(this).load(data.gambar).centerCrop().into(img_edit_tugas)

            } else if (data.video != "") {
                frame_edit_tugas.visibility = View.VISIBLE
                vv_edit_tugas.visibility = View.VISIBLE
                img_edit_tugas.visibility = View.GONE
                vv_edit_tugas.setVideoURI(Uri.parse(data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_edit_tugas.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_tugas)
                vv_edit_tugas.seekTo(10)
                btn_tutup_edit_tugas.visibility = View.VISIBLE

            }

            btn_simpan_edit_tugas.setOnClickListener {


                btn_simpan_edit_tugas.isEnabled = false
                btn_simpan_edit_tugas.text = "Loading"
                if(tv_kelas_edit_tugas.text.toString().isEmpty() || tv_aspek_edit_tugas.text.toString().isEmpty() ||tv_deskripsi_edit_tugas.text.toString().isEmpty() ||tv_tanggal_edit_tugas.text.toString().isEmpty() ||tv_jam_edit_tugas.text.toString().isEmpty() ) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_tugas.isEnabled = true
                    btn_simpan_edit_tugas.text = "Simpan"

                }else {
                    if (tv_link_edit_tugas.text.toString().isValidUrl() || tv_link_edit_tugas.text.toString().equals("")) {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(data.id_tugas + "." + getFileExtension(image_uri!! ))

                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension( image_uri!!) == "jpeg") {
                                //storage = FirebaseStorage.getInstance().reference.child("Tugas").child(sharedPreferences.getString("id_tugas", "").toString()).child(builder.toString())
                                storage.child("Tugas").child(data.id_tugas!!).child(builder.toString()).putFile(image_uri!!).addOnSuccessListener {
                                    storage.child("Tugas").child(data.id_tugas!!).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                            db.collection("Tugas").document(data.id_tugas!!)
                                                .update(mapOf(
                                                    "isi" to tv_deskripsi_edit_tugas.text.toString() ,
                                                    "judul" to tv_aspek_edit_tugas.text.toString(),
                                                    "jam" to tv_jam_edit_tugas.text.toString() ,
                                                    "tanggal" to tv_tanggal_edit_tugas.text.toString(),
                                                    "link" to tv_link_edit_tugas.text.toString(),
                                                    "kelas" to tv_kelas_edit_tugas.text.toString(),
                                                    "gambar" to  it.toString() ,
                                                    "video" to "",
                                                    "jumlah" to "20/20"
                                                )
                                               )}.addOnCompleteListener {
                                            val intent: Intent =  Intent(this@EditTugasActivity,TugasActivity::class.java  )
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

                                storage.child("Tugas").child(data.id_tugas!!).child(builder.toString()).putFile(image_uri!!) .addOnSuccessListener {
                                    storage.child("Tugas").child(data.id_tugas!!)
                                        .child(builder.toString()).downloadUrl.addOnSuccessListener {

                                            db.collection("Tugas").document(data.id_tugas!!)
                                                .update(mapOf(
                                                    "isi" to tv_deskripsi_edit_tugas.text.toString() ,
                                                    "judul" to tv_aspek_edit_tugas.text.toString(),
                                                    "jam" to tv_jam_edit_tugas.text.toString() ,
                                                    "tanggal" to tv_tanggal_edit_tugas.text.toString(),
                                                    "link" to tv_link_edit_tugas.text.toString(),
                                                    "kelas" to tv_kelas_edit_tugas.text.toString(),
                                                    "gambar" to  "" ,
                                                    "video" to it.toString(),
                                                    "jumlah" to "20/20"
                                                )
                                                )}.addOnCompleteListener {

                                            val intent: Intent =  Intent(this@EditTugasActivity,TugasActivity::class.java  )
                                            startActivity(intent)
                                        }
                                }
                            }

                        }else if (image_uri == null){
                            db.collection("Tugas").document(data.id_tugas!!)
                                .update(mapOf(
                                    "isi" to tv_deskripsi_edit_tugas.text.toString() ,
                                    "judul" to tv_aspek_edit_tugas.text.toString(),
                                    "jam" to tv_jam_edit_tugas.text.toString() ,
                                    "tanggal" to tv_tanggal_edit_tugas.text.toString(),
                                    "link" to tv_link_edit_tugas.text.toString(),
                                    "kelas" to tv_kelas_edit_tugas.text.toString(),
                                    "gambar" to  "" ,
                                    "video" to "",
                                    "jumlah" to "20/20"
                                )
                                ).addOnCompleteListener {

                                    val intent: Intent =  Intent(this@EditTugasActivity,TugasActivity::class.java  )
                                    startActivity(intent)
                            Toast.makeText(
                                this,
                                "Simpan Berhasil",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        }

                            }else {
                        tv_link_edit_tugas.error = "Pastikan tautan sudah benar"
                    }
                            }
            }


        } else if (intent.getStringExtra("jenis") == "TAMBAH_TUGAS") {
            // simpan
            //link
            btn_simpan_edit_tugas.setOnClickListener {
                btn_simpan_edit_tugas.isEnabled = false
                btn_simpan_edit_tugas.text = "Loading"

                if(tv_kelas_edit_tugas.text.toString().isEmpty() || tv_aspek_edit_tugas.text.toString().isEmpty() ||tv_deskripsi_edit_tugas.text.toString().isEmpty() ||tv_tanggal_edit_tugas.text.toString().isEmpty() ||tv_jam_edit_tugas.text.toString().isEmpty() ) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_edit_tugas.isEnabled = true
                    btn_simpan_edit_tugas.text = "Simpan"

                }else {
                    if (tv_link_edit_tugas.text.toString().isValidUrl() || tv_link_edit_tugas.text.toString().equals("")) {
                        if (image_uri != null) {
                            val builder = StringBuilder()
                            builder.append(currentDateAndTime + "." + getFileExtension(image_uri!! ))

                            if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(image_uri!!) == "jpeg") {
                                storage.child("Tugas").child(currentDateAndTime).child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Tugas").child(currentDateAndTime).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val tugas = hashMapOf(
                                                    "id_tugas" to  currentDateAndTime,
                                                    "kelas" to tv_kelas_edit_tugas.text.toString(),
                                                    "judul" to tv_aspek_edit_tugas.text.toString(),
                                                    "isi" to  tv_deskripsi_edit_tugas.text.toString(),
                                                    "tanggal" to  tv_tanggal_edit_tugas.text.toString(),
                                                    "jam" to  tv_jam_edit_tugas.text.toString(),
                                                    "jumlah" to  "20/20",
                                                    "gambar" to   it.toString(),
                                                    "video" to  "",
                                                    "link" to  tv_link_edit_tugas.text.toString()
                                                )
                                                db.collection("Tugas").document(currentDateAndTime).set(tugas)}.addOnCompleteListener {
                                                val intent: Intent =  Intent(this@EditTugasActivity,TugasActivity::class.java  )
                                                startActivity(intent)
                                                Toast.makeText(  this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                                            }

                                    }
                            } else if (getFileExtension(image_uri!!) == "mp4") {
                                storage.child("Tugas").child(builder.toString())
                                    .putFile(image_uri!!)
                                    .addOnSuccessListener {
                                        storage.child("Tugas").child(currentDateAndTime)
                                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val tugas = hashMapOf(
                                                    "id_tugas" to  currentDateAndTime,
                                                    "kelas" to tv_kelas_edit_tugas.text.toString(),
                                                    "judul" to tv_aspek_edit_tugas.text.toString(),
                                                    "isi" to  tv_deskripsi_edit_tugas.text.toString(),
                                                    "tanggal" to  tv_tanggal_edit_tugas.text.toString(),
                                                    "jam" to  tv_jam_edit_tugas.text.toString(),
                                                    "jumlah" to  "20/20",
                                                    "gambar" to   "",
                                                    "video" to  it.toString(),
                                                    "link" to  tv_link_edit_tugas.text.toString()
                                                )
                                                db.collection("Tugas").document(currentDateAndTime.toString()).set(tugas)
                                            }.addOnCompleteListener {
                                                val intent: Intent =Intent(this@EditTugasActivity,TugasActivity::class.java)
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
                        } else {
                            val tugas = hashMapOf(
                                "id_tugas" to  currentDateAndTime,
                                "kelas" to tv_kelas_edit_tugas.text.toString(),
                                "judul" to tv_aspek_edit_tugas.text.toString(),
                                "isi" to  tv_deskripsi_edit_tugas.text.toString(),
                                "tanggal" to  tv_tanggal_edit_tugas.text.toString(),
                                "jam" to  tv_jam_edit_tugas.text.toString(),
                                "jumlah" to  "20/20",
                                "gambar" to   "",
                                "video" to  "",
                                "link" to  tv_link_edit_tugas.text.toString()
                            )
                            db.collection("Tugas").document(currentDateAndTime).set(tugas).addOnSuccessListener {
                                val intent: Intent =
                                    Intent(this@EditTugasActivity, TugasActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        tv_link_edit_tugas.error = "Pastikan tautan sudah benar"
                        btn_simpan_edit_tugas.isEnabled = false
                    }
                }
            }
        }

        //tutup gambar
        // hapus video belum bisa
        btn_tutup_edit_tugas.setOnClickListener {
            frame_edit_tugas.visibility = View.GONE
            img_edit_tugas.setImageResource(0)
            img_edit_tugas.visibility = View.GONE
            vv_edit_tugas.setVideoURI(Uri.parse(""))
            vv_edit_tugas.visibility = View.GONE
            btn_tutup_edit_tugas.visibility = View.GONE
            image_uri=null
        }

        //kembali
        img_back_edit_tugas.setOnClickListener {
            val intent: Intent = Intent(this@EditTugasActivity, TugasActivity::class.java)
            startActivity(intent)

        }


        //tanggal
        tv_tanggal_edit_tugas.setText(SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis()))
        var cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd-MM-yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                tv_tanggal_edit_tugas.setText(sdf.format(cal.time))

            }

        btn_tanggal_edit_tugas.setOnClickListener {
            DatePickerDialog(
                this@EditTugasActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        //jam
        btn_jam_edit_tugas.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tv_jam_edit_tugas.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        //link
        img_link_edit_tugas.setOnClickListener {
            input_link_edit_tugas.visibility = View.VISIBLE

        }

        //untuk kelas
        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_text, items
        )
        (dropdown_kelas_edit_tugas.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        //untuk aspek
        val items2 = listOf("Keterampilan", "Motorik", "Kognitif", "Agama", "Berbahasa")
        val adapter2 = ArrayAdapter(
            this,
            R.layout.dropdown_text, items2
        )
        (dropdown_edit_tugas_aspek.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        //galeri
        img_gambar_edit_tugas.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/* video/*"
            startActivityForResult(intent, 1)
        }

        //kamera
        img_kamera_edit_tugas.setOnClickListener {
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
                frame_edit_tugas.visibility = View.VISIBLE



                img_edit_tugas.visibility = View.VISIBLE
                vv_edit_tugas.visibility = View.GONE
                img_edit_tugas.setImageURI(image_uri)

            } else if (data?.data.toString().contains("video")) {
                frame_edit_tugas.visibility = View.VISIBLE
                image_uri = data?.data
                vv_edit_tugas.visibility = View.VISIBLE
                img_edit_tugas.visibility = View.GONE
                vv_edit_tugas.setVideoURI(image_uri)
                var media_Controller: MediaController = MediaController(this)
                vv_edit_tugas.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_tugas)
                vv_edit_tugas.seekTo(10)
            }
        }

        //ambil gambar kamera
        else if (resultCode == Activity.RESULT_OK) {
            frame_edit_tugas.visibility = View.VISIBLE
            //set image captured to image view
            img_edit_tugas.visibility = View.VISIBLE
            vv_edit_tugas.visibility = View.GONE
            img_edit_tugas.setImageURI(image_uri)
        }

        btn_tutup_edit_tugas.visibility = View.VISIBLE
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