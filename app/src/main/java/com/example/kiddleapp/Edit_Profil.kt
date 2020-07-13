package com.example.kiddleapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import android.widget.ViewAnimator
import kotlinx.android.synthetic.main.activity_edit__profil.*
import kotlinx.android.synthetic.main.activity_rapor.*

class Edit_Profil : AppCompatActivity() {

    lateinit var  img_location: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__profil)

        if(intent.getStringExtra("jenis") == "EDIT_PROFIL") {
            //biar langsung keisi. kurang bagian gambar
            edit_nama_profil.setText(intent.getStringExtra("nama_profil"))
            edit_nomor_profil.setText(intent.getStringExtra("nomor_profil"))
            edit_kontak_profil.setText(intent.getStringExtra("kontak_profil"))
            edit_password_profil.setText(intent.getStringExtra("password_profil"))
            dropdown_value_wali_kelas.setText(intent.getStringExtra("jabatan_profil"))

            //gambar masih manual
            img_edit_profil.setImageResource(R.drawable.avatar)

            //matikan input dan tampilkan warning
            tv_warning_nomor.visibility = View.VISIBLE
            tv_warning_wali_kelas.visibility = View.VISIBLE
            edit_nomor_profil.isEnabled = false
            dropdown_wali_kelas.isEnabled = false
        } else {
            //assign dropdown wali kelas
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_wali_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_edit_profil.setOnClickListener {
            onBackPressed()
        }

        //diganti pake firebase
        btn_simpan_profil.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //buat pilih foto dari galeri
        btn_upload_profil.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    //buat dapetin extension gambar. dipake nanti buat firebase
    private fun getFileExtension(imgLocation:Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgLocation))
    }

    //mengisi gambar yang telah dipilih ke imageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            img_location = data.data!!
            img_edit_profil.setImageURI(img_location)
        }
    }
}