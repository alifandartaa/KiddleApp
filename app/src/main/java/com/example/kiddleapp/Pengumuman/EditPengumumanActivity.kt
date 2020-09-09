package com.example.kiddleapp.Pengumuman

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_edit_pengumuman.*

class EditPengumumanActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pengumuman)
        if(intent.getStringExtra("jenis") == "EDIT_PENGUMUMAN") {
            //mengambil data dari halaman sebelumnya
            val data = intent.getParcelableExtra<Pengumuman>("data")
            tv_deskripsi_edit_pengumuman.setText(data.isi)
            tv_judul_edit_pengumuman.setText(data.judul)

            if (!data.gambar.isNullOrEmpty()) {
                img_edit_pengumuman.visibility = View.VISIBLE
                vv_edit_pengumuman.visibility = View.GONE
                btn_tutup_edit_pengumuman.visibility = View.VISIBLE
                Glide.with(this).load(data.gambar).centerCrop().into(img_edit_pengumuman)
            } else if (!data.video.isNullOrEmpty()) {
                vv_edit_pengumuman.visibility = View.VISIBLE
                img_edit_pengumuman.visibility = View.GONE
                vv_edit_pengumuman.setVideoURI(Uri.parse(data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_edit_pengumuman.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_pengumuman)
                vv_edit_pengumuman.seekTo(10)
                btn_tutup_edit_pengumuman.visibility = View.VISIBLE

            }
        }

        //tutup gambar
       // hapus video belum bisa
        btn_tutup_edit_pengumuman.setOnClickListener {
            img_edit_pengumuman.setImageResource(0)
            img_edit_pengumuman.visibility = View.GONE
            btn_tutup_edit_pengumuman.visibility = View.GONE
        }

        //kembali
        img_back_edit_pengumuman.setOnClickListener {
            onBackPressed()
        }


        // simpan
        btn_simpan_edit_pengumuman.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }



        //galeri
        img_gambar_edit_pengumuman.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/* video/*"
            startActivityForResult(intent, 1)
        }

        //kamera
        img_kamera_edit_pengumuman.setOnClickListener {
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
                img_edit_pengumuman.visibility = View.VISIBLE
                vv_edit_pengumuman.visibility = View.GONE
                img_edit_pengumuman.setImageURI(data?.data)

            } else if (data?.data.toString().contains("video")) {
                vv_edit_pengumuman.visibility = View.VISIBLE
                img_edit_pengumuman.visibility = View.GONE
                vv_edit_pengumuman.setVideoURI(data?.data)
                var media_Controller: MediaController = MediaController(this)
                vv_edit_pengumuman.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_pengumuman)
                vv_edit_pengumuman.seekTo(10)
            }
        }

        //ambil gambar kamera
        else if (resultCode == Activity.RESULT_OK) {
            //set image captured to image view
            img_edit_pengumuman.visibility = View.VISIBLE
            vv_edit_pengumuman.visibility = View.GONE
            img_edit_pengumuman.setImageURI(image_uri)
        }

        btn_tutup_edit_pengumuman.visibility = View.VISIBLE
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