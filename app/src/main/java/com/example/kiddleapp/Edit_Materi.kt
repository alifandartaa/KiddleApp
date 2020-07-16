package com.example.kiddleapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.model.Model_Jurnal
import com.example.kiddleapp.model.Model_Kegiatan
import com.example.kiddleapp.model.Model_Materi
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.activity_detail_tugas.*
import kotlinx.android.synthetic.main.activity_edit_jurnal.*
import kotlinx.android.synthetic.main.activity_edit_kegiatan.*
import kotlinx.android.synthetic.main.activity_edit_materi.*
import kotlinx.android.synthetic.main.activity_edit_tugas.*

import java.util.*
import java.text.SimpleDateFormat as SimpleDateFormat1

class Edit_Materi : AppCompatActivity() {

    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_materi)
        if(intent.getStringExtra("jenis") == "EDIT_MATERI") {
            //mengambil data dari halaman sebelumnya
            val data = intent.getParcelableExtra<Model_Materi>("data")
            tv_deskripsi_edit_materi.setText(data.isi)
            tv_judul_edit_materi.setText(data.judul)

            if (data.gambar != 0) {
                img_edit_materi.setVisibility(View.VISIBLE);
                vv_edit_materi.setVisibility(View.GONE);
                btn_tutup_edit_materi.setVisibility(View.VISIBLE);
                img_edit_materi.setImageResource(data.gambar)
            } else if (data.video != 0) {
                vv_edit_materi.setVisibility(View.VISIBLE);
                img_edit_materi.setVisibility(View.GONE);
                vv_edit_materi.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + data.video))
                var media_Controller: MediaController = MediaController(this)
                vv_edit_materi.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_materi)
                vv_edit_materi.seekTo(10);
                btn_tutup_edit_materi.setVisibility(View.VISIBLE);

            }
        }

        //tutup gambar
       // hapus video belum bisa
        btn_tutup_edit_materi.setOnClickListener {
            img_edit_materi.setImageResource(0)
            img_edit_materi.setVisibility(View.GONE);
            btn_tutup_edit_materi.setVisibility(View.GONE);
        }

        //kembali
        img_back_edit_materi.setOnClickListener {
            onBackPressed()
        }


        // simpan
        btn_simpan_edit_materi.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }



        //galeri
        img_gambar_edit_materi.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/* video/*"
            startActivityForResult(intent, 1)
        }

        //kamera
        img_kamera_edit_materi.setOnClickListener {
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
                img_edit_materi.setVisibility(View.VISIBLE);
                vv_edit_materi.setVisibility(View.GONE);
                img_edit_materi.setImageURI(data?.data)

            } else if (data?.data.toString().contains("video")) {
                vv_edit_materi.setVisibility(View.VISIBLE);
                img_edit_materi.setVisibility(View.GONE);
                vv_edit_materi.setVideoURI(data?.data)
                var media_Controller: MediaController = MediaController(this)
                vv_edit_materi.setMediaController(media_Controller)
                media_Controller.setAnchorView(vv_edit_materi)
                vv_edit_materi.seekTo(10);
            }
        }

        //ambil gambar kamera
        else if (resultCode == Activity.RESULT_OK) {
            //set image captured to image view
            img_edit_materi.setVisibility(View.VISIBLE);
            vv_edit_materi.setVisibility(View.GONE);
            img_edit_materi.setImageURI(image_uri)
        }

        btn_tutup_edit_materi.setVisibility(View.VISIBLE);
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
        img_link_edit_materi.setOnClickListener {
            input_link_edit_materi.setVisibility(View.VISIBLE)
//nanti pakai youtube api
//            val videoUrl:String = getUrlVideoRTSP(url);
//            vv_tambah_tugas.setVisibility(View.VISIBLE);
//            val mediaController =
//                MediaController(this)
//            mediaController.setAnchorView(vv_tambah_tugas)
//            val uri = Uri.parse("rtsp://r7---sn-4g57kue6.googlevideo.com/Ck0LENy73wIaRAmk3cJBg-iaXhMYDSANFC3u0pRWMOCoAUIJbXYtZ29vZ2xlSARSBXdhdGNoYIaluaTkzciOVooBCzVxRjNraG5XcXdnDA==/D693A8E7577C3A29E60C292B42C9C87D7C25A565.762A63DC4CA0A028DA83256C6A79E5F160CBEDA3/yt6/1/video.3gp")
//            vv_tambah_tugas.setMediaController(mediaController)
//            vv_tambah_tugas.setVideoURI(uri)
//            vv_tambah_tugas.requestFocus()
//            vv_tambah_tugas.start()

        }

    }
}