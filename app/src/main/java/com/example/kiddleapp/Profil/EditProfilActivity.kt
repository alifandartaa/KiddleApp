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
import com.example.kiddleapp.Guru.GuruActivity
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Murid.EditMuridActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.example.kiddleapp.Sekolah.ProfilSekolahActivity
import com.google.common.io.Files.getFileExtension
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit__murid.*
import kotlinx.android.synthetic.main.activity_edit__profil.*

class EditProfilActivity : AppCompatActivity() {

    lateinit var img_location: Uri
    lateinit var avatar:String
    var flag:Boolean = true
    var storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__profil)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)


        val data = intent.getParcelableExtra<Guru>("data")

        if (intent.getStringExtra("jenis") == "EDIT_PROFIL") {
            edit_nama_profil.setText(data.nama)
            edit_nomor_profil.setText(data.nomor)
            edit_kontak_profil.setText(data.kontak)
            edit_password_profil.setText(data.password)
            dropdown_value_wali_kelas.setText(data.jabatan)
            Glide.with(this).load(data.avatar).centerCrop().into(img_edit_profil)
            img_location = Uri.parse(data.avatar)

            //matikan input dan tampilkan warning
            tv_warning_nomor.visibility = View.VISIBLE
            tv_warning_wali_kelas.visibility = View.VISIBLE
            edit_nomor_profil.isEnabled = false
            dropdown_wali_kelas.isEnabled = false

            //diganti pake firebase
            btn_simpan_profil.setOnClickListener {
                btn_simpan_profil.isEnabled = false
                btn_simpan_profil.text = "Loading"

                if(edit_nama_profil.text.toString().isEmpty() || edit_password_profil.text.toString().isEmpty()) {
                    Toast.makeText(this, "Pastikan Nama dan Kata Sandi terisi!", Toast.LENGTH_SHORT).show()
                    btn_simpan_profil.isEnabled = true
                    btn_simpan_profil.text = "Simpan"
                } else {
                    if (getFileExtension(img_location!!) == "jpg" || getFileExtension(img_location!!) == "png" || getFileExtension( img_location!! ) == "jpeg") {
                        val builder = StringBuilder()
                        builder.append(sharedPreferences.getString("id_guru", "")).append(".").append(getFileExtension(img_location))
                        storage.child("Guru").child(sharedPreferences.getString("id_guru", "").toString()).child(builder.toString()).putFile(img_location).addOnSuccessListener {
                            storage.child("Guru").child(sharedPreferences.getString("id_guru", "").toString()).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                avatar = it.toString()
                                db.document("Guru/${sharedPreferences.getString("id_guru", "")}").update(
                                    mapOf("avatar" to it.toString(),
                                        "nama" to edit_nama_profil.text.toString(),
                                        "kontak" to edit_kontak_profil.text.toString(),
                                        "password" to edit_password_profil.text.toString())
                                )
                                Toast.makeText(this, "Mohon tunggu, profil anda akan segara diubah!", Toast.LENGTH_SHORT).show()
                            }.addOnSuccessListener {
                                val intent: Intent = Intent(this@EditProfilActivity, ProfilActivity::class.java) .putExtra(
                                    "tipeAkses",
                                    "PROFIL"
                                )
                                startActivity(intent)
                            }
                        }

                    }else{
                        avatar = data.avatar.toString()
                        db.document("Guru/${sharedPreferences.getString("id_guru", "")}").update(
                            mapOf("avatar" to data.avatar,
                                "nama" to edit_nama_profil.text.toString(),
                                "kontak" to edit_kontak_profil.text.toString(),
                                "password" to edit_password_profil.text.toString())
                        )
                        Toast.makeText(this, "Mohon tunggu, profil anda akan segara diubah!", Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this@EditProfilActivity, ProfilActivity::class.java)
                            .putExtra(
                                "tipeAkses",
                                "PROFIL"
                            )
                        startActivity(intent)
                    }
                }

            }


        } else if (intent.getStringExtra("jenis") == "EDIT_GURU") {
            //assign dropdown wali kelas
            edit_nama_profil.setText(data.nama)
            edit_nomor_profil.setText(data.nomor)
            edit_kontak_profil.setText(data.kontak)
            edit_password_profil.setText(data.password)
            dropdown_value_wali_kelas.setText(data.jabatan)
            Glide.with(this).load(data.avatar).centerCrop().into(img_edit_profil)
            img_location = Uri.parse(data.avatar)

            val items = listOf(
                "Bintang Kecil",
                "Bintang Besar",
                "Bulan Kecil",
                "Bulan Besar",
                "Admin",
                "Kepala Sekolah"
            )
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_wali_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            btn_simpan_profil.setOnClickListener {
                btn_simpan_profil.isEnabled = false
                btn_simpan_profil.text = "Loading"

                if (edit_nama_profil.text.toString()
                        .isEmpty() || edit_password_profil.text.toString()
                        .isEmpty() || edit_nomor_profil.text.toString()
                        .isEmpty() || edit_kontak_profil.text.toString()
                        .isEmpty() || dropdown_value_wali_kelas.toString().isEmpty())
                {
                    Toast.makeText(this@EditProfilActivity, "Pastikan semua kolom dan foto terisi!", Toast.LENGTH_SHORT).show()
                    btn_simpan_profil.isEnabled = true
                    btn_simpan_profil.text = "Simpan"
                }

                else {
                    if (
                        getFileExtension(img_location!!) == "jpg" || getFileExtension(img_location!!) == "png" || getFileExtension( img_location!! ) == "jpeg") {
                            storage.child("Guru/"+data.nomor!!+"/"+data.nomor!!+".jpg").delete().addOnSuccessListener {

                            }
                            storage.child("Guru/"+data.nomor!!+"/"+data.nomor!!+".jpeg").delete().addOnSuccessListener {

                            }
                            storage.child("Guru/"+data.nomor!!+"/"+data.nomor!!+".png").delete().addOnSuccessListener {

                            }
                            storage.child("Guru/"+data.nomor!!+"/"+data.nomor!!+".mp4").delete().addOnSuccessListener {

                            }

                        val builder = StringBuilder()
                        builder.append( edit_nomor_profil.text.toString() + "." + getFileExtension(img_location!! ))
                        storage.child("Guru").child(edit_nomor_profil.text.toString()!!).child(builder.toString())
                            .putFile(img_location).addOnSuccessListener {
                                storage.child("Guru").child(edit_nomor_profil.text.toString()!!).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                avatar = it.toString()
                                db.collection("Guru").document(edit_nomor_profil.text.toString()!!).update(
                                    mapOf("avatar" to it.toString(),
                                        "nama" to edit_nama_profil.text.toString(),
                                        "kontak" to edit_kontak_profil.text.toString(),
                                        "password" to edit_password_profil.text.toString(),
                                        "jabatan" to dropdown_value_wali_kelas.text.toString(),
                                        "nomor" to  edit_nomor_profil.text.toString()
                                    )
                                )

                                Toast.makeText(this, "Mohon tunggu, data guru akan segara diubah!", Toast.LENGTH_SHORT).show()
                            }.addOnSuccessListener {
                                val intent: Intent = Intent(this@EditProfilActivity, GuruActivity::class.java)
                                startActivity(intent)
                            }
                        }


                    }else{
                        avatar = data.avatar.toString()
                        db.collection("Guru").document(edit_nomor_profil.text.toString()!!).update(
                            mapOf("avatar" to data.avatar,
                                "nama" to edit_nama_profil.text.toString(),
                                "kontak" to edit_kontak_profil.text.toString(),
                                "password" to edit_password_profil.text.toString(),
                            "jabatan" to dropdown_value_wali_kelas.text.toString(),
                            "nomor" to  edit_nomor_profil.text.toString())
                        )
                        Toast.makeText(this, "Mohon tunggu, profil anda akan segara diubah!", Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this@EditProfilActivity, GuruActivity::class.java)
                        startActivity(intent)
                    }

                }
            }
        }else if (intent.getStringExtra("jenis") == "TAMBAH_GURU") {
            storage = FirebaseStorage.getInstance().reference
            val items = listOf(
                "Bintang Kecil",
                "Bintang Besar",
                "Bulan Kecil",
                "Bulan Besar",
                "Admin",
                "Kepala Sekolah"
            )
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_wali_kelas.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            btn_simpan_profil.setOnClickListener {
                btn_simpan_profil.isEnabled = false
                btn_simpan_profil.text = "Loading"

                if (edit_nama_profil.text.toString()
                        .isEmpty() || edit_password_profil.text.toString()
                        .isEmpty() || edit_nomor_profil.text.toString()
                        .isEmpty() || edit_kontak_profil.text.toString()
                        .isEmpty() || dropdown_value_wali_kelas.text.toString().isEmpty()
                    || img_location == null
                ) {
                    Toast.makeText(
                        this,
                        "Pastikan semua kolom dan foto terisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_profil.isEnabled = true
                    btn_simpan_profil.text = "Simpan"
                } else {
                    val builder = StringBuilder()
                    builder.append(
                        edit_nomor_profil.text.toString() + "." + getFileExtension(
                            img_location!!
                        )
                    )
                    storage.child("Guru").child(edit_nomor_profil.text.toString()!!)
                        .child(builder.toString())
                        .putFile(img_location).addOnSuccessListener {
                            storage.child("Guru").child(edit_nomor_profil.text.toString()!!)
                                .child(builder.toString()).downloadUrl.addOnSuccessListener {
                                    val guru = hashMapOf(
                                        "avatar" to it.toString(),
                                        "nama" to edit_nama_profil.text.toString(),
                                        "kontak" to edit_kontak_profil.text.toString(),
                                        "password" to edit_password_profil.text.toString(),
                                        "jabatan" to dropdown_value_wali_kelas.text.toString(),
                                        "nomor" to  edit_nomor_profil.text.toString())

                                    db.collection("Guru").document(edit_nomor_profil.text.toString()).set(guru)}.addOnCompleteListener {
                                    val intent: Intent =  Intent(this, GuruActivity::class.java  )
                                    startActivity(intent)
                                    Toast.makeText(  this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                                }

                        }
                }


            }
        }


        //intent untuk kembali ke halaman sebelumnya
        img_back_edit_profil.setOnClickListener {
            onBackPressed()
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
            Glide.with(this).load(img_location).centerCrop().into(img_edit_profil)

        }
        flag = false
    }
}