package com.example.kiddleapp.Profil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit__profil.*

class EditProfilActivity : AppCompatActivity() {

    lateinit var img_location: Uri
    lateinit var avatar:String
    var flag:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__profil)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)
        var storage:StorageReference

        if (intent.getStringExtra("jenis") == "EDIT_PROFIL") {
            //biar langsung keisi. kurang bagian gambar
            edit_nama_profil.setText(intent.getStringExtra("nama_profil"))
            edit_nomor_profil.setText(intent.getStringExtra("nomor_profil"))
            edit_kontak_profil.setText(intent.getStringExtra("kontak_profil"))
            edit_password_profil.setText(intent.getStringExtra("password_profil"))
            dropdown_value_wali_kelas.setText(intent.getStringExtra("jabatan_profil"))

            Glide.with(this).load(intent.getStringExtra("avatar")).centerCrop().into(img_edit_profil)

            //matikan input dan tampilkan warning
            tv_warning_nomor.visibility = View.VISIBLE
            tv_warning_wali_kelas.visibility = View.VISIBLE
            edit_nomor_profil.isEnabled = false
            dropdown_wali_kelas.isEnabled = false
        } else {
            //assign dropdown wali kelas
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
            val adapter = ArrayAdapter(
                this,
                R.layout.dropdown_text, items
            )
            (dropdown_wali_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_edit_profil.setOnClickListener {
            onBackPressed()
        }

        //diganti pake firebase
        btn_simpan_profil.setOnClickListener {
            btn_simpan_profil.isEnabled = false
            btn_simpan_profil.text = "Loading"

            if(edit_nama_profil.text.toString().isEmpty() || edit_password_profil.text.toString().isEmpty() || flag) {
                Toast.makeText(this, "Harap pilih Foto dan pastikan Nama dan Kata Sandi terisi!", Toast.LENGTH_SHORT).show()
                btn_simpan_profil.isEnabled = true
                btn_simpan_profil.text = "Simpan"
            } else {
                val builder = StringBuilder()
                builder.append(sharedPreferences.getString("id_guru", "")).append(".").append(getFileExtension(img_location))

                storage = FirebaseStorage.getInstance().reference.child("Guru").child(sharedPreferences.getString("id_guru", "").toString()).child(builder.toString())
                storage.putFile(img_location).addOnSuccessListener {
                    storage.downloadUrl.addOnSuccessListener {
                        avatar = it.toString()
                        db.document("Guru/${sharedPreferences.getString("id_guru", "")}").update(
                            mapOf("avatar" to it.toString(), "nama" to edit_nama_profil.text.toString(),
                            "kontak" to edit_kontak_profil.text.toString(), "password" to edit_password_profil.text.toString())
                        )
                        Toast.makeText(this, "Mohon tunggu, profil anda akan segara diubah!", Toast.LENGTH_SHORT).show()
                    }.addOnSuccessListener {
                        sharedPreferences.edit().putString("avatar", avatar).apply()
                        sharedPreferences.edit().putString("nama", edit_nama_profil.text.toString()).apply()
                        onBackPressed()
                    }
                }
            }

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

        flag = false
    }
}