package com.example.kiddleapp.Murid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_edit__murid.*

class EditMuridActivity : AppCompatActivity() {

    lateinit var img_location: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__murid)

        if (intent.getStringExtra("jenis") == "EDIT_PROFIL") {
            //biar langsung keisi. kurang bagian gambar
            edit_nama_murid.setText(intent.getStringExtra("nama_murid"))
            edit_nomor_murid.setText(intent.getStringExtra("nomor_murid"))
            edit_password_murid.setText(intent.getStringExtra("password_murid"))
            dropdown_value_kelas_murid.setText(intent.getStringExtra("kelas_murid"))
            edit_ttl_murid.setText(intent.getStringExtra("ttl_murid"))
            edit_alamat_murid.setText(intent.getStringExtra("alamat_murid"))
            edit_ayah_murid.setText(intent.getStringExtra("ayah_murid"))
            edit_kontak_ayah.setText(intent.getStringExtra("kontak_ayah"))
            edit_ibu_murid.setText(intent.getStringExtra("ibu_murid"))
            edit_kontak_ibu.setText(intent.getStringExtra("kontak_ibu"))

            //gambar masih manual
            img_edit_murid.setImageResource(R.drawable.avatar)

            //tampilkan warning dan matikan input
            waring_nomor_murid.visibility = View.VISIBLE
            warning_kelas_murid.visibility = View.VISIBLE
            edit_nomor_murid.isEnabled = false
            dropdown_kelas_murid.isEnabled = false
        } else {
            //assign dropdown kelas
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
            val adapter = ArrayAdapter(
                this,
                R.layout.dropdown_text, items
            )
            (dropdown_kelas_murid.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_edit_murid.setOnClickListener {
            onBackPressed()
        }

        //diganti pake firebase
        btn_simpan_murid.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //buat pilih foto dari galeri
        btn_upload_murid.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            img_location = data.data!!
            img_edit_murid.setImageURI(img_location)
        }
    }
}