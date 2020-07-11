package com.example.kiddleapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit__sekolah.*

class Edit_Sekolah : AppCompatActivity() {

    lateinit var  img_location: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__sekolah)

        //barangkali bingung mau edit apa jadi di assign dulu value nya. kurang gambar sekolah nya
        edit_nama_sekolah.setText(intent.getStringExtra("nama_sekolah"))
        edit_alamat_sekolah.setText(intent.getStringExtra("alamat_sekolah"))
        edit_kontak_sekolah.setText(intent.getStringExtra("kontak_sekolah"))
        edit_deskripsi_sekolah.setText(intent.getStringExtra("deskripsi_sekolah"))
        edit_visi_sekolah.setText(intent.getStringExtra("visi_sekolah"))
        edit_misi_sekolah.setText(intent.getStringExtra("misi_sekolah"))
        edit_prestasi_sekolah.setText(intent.getStringExtra("prestasi_sekolah"))
        edit_fasilitas_sekolah.setText(intent.getStringExtra("prestasi_sekolah"))

        // intent untuk kembali ke halaman sebelumnya
        img_back_edit_sekolah.setOnClickListener {
            onBackPressed()
        }

        //ganti pake firebase untuk menyimpan perubahan
        btn_simpan_sekolah.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //buat pilih foto dari galeri
        btn_upload_sekolah.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    //buat dapetin extension gambar nanti di firebase dipake
    private fun getFileExtension(imgLocation: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgLocation))
    }

    //mengisi gambar yang telah dipilih ke imageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            img_location = data.data!!
            img_edit_foto_sekolah.setImageURI(img_location)
        }
    }
}