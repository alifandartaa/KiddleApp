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
import com.bumptech.glide.Glide
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.Jurnal.Model.Jurnal
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.google.common.io.Files
import com.google.common.io.Files.getFileExtension
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail__murid.*
import kotlinx.android.synthetic.main.activity_edit__murid.*
import kotlinx.android.synthetic.main.activity_edit__profil.*
import kotlinx.android.synthetic.main.activity_edit_jurnal.*

class EditMuridActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference
    lateinit var listMurid: Murid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__murid)
        val data = intent.getParcelableExtra<Murid>("data")

        if (intent.getStringExtra("jenis") == "EDIT_MURID") {

            //biar langsung keisi. kurang bagian gambar
            edit_nama_murid.setText(data.nama)
            edit_angkatan_murid.setText(data.angkatan)
            edit_nomor_murid.setText(data.nomor)
            edit_password_murid.setText(data.password)
            dropdown_value_kelas_murid.setText(data.kelas)
            edit_ttl_murid.setText(data.ttl)
            edit_alamat_murid.setText(data.alamat)
            edit_ayah_murid.setText(data.ayah)
            edit_kontak_ayah.setText(data.kontakAyah)
            edit_ibu_murid.setText(data.ibu)
            edit_kontak_ibu.setText(data.kontakIbu)
            if(data.avatar != null){
                Glide.with(this).load(data.avatar).centerCrop().into(img_edit_murid)
                image_uri = Uri.parse(data.avatar)
            }

            //tampilkan warning dan matikan input
//            waring_nomor_murid.visibility = View.VISIBLE
//            warning_kelas_murid.visibility = View.VISIBLE
//            edit_nomor_murid.isEnabled = false
//            dropdown_kelas_murid.isEnabled = false

            //assign dropdown kelas
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_kelas_murid.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            //intent untuk kembali ke halaman sebelumnya
            img_back_edit_murid.setOnClickListener {
                val intent: Intent = Intent(this@EditMuridActivity, DetailMuridActivity::class.java).putExtra("data",data)
                startActivity(intent)
                finish()
            }

            btn_simpan_murid.setOnClickListener {
                btn_simpan_murid.isEnabled = false
                btn_simpan_murid.text = "Loading"

                if (edit_nama_murid.text.toString().isEmpty() || edit_password_murid.text.toString().isEmpty()
                    || edit_ttl_murid.text.toString().isEmpty() || edit_alamat_murid.text.toString().isEmpty()
                    || edit_ayah_murid.text.toString().isEmpty() || edit_kontak_ayah.text.toString().isEmpty()
                    || edit_ibu_murid.text.toString().isEmpty() || edit_kontak_ibu.text.toString().isEmpty()
                    || edit_angkatan_murid.text.toString().isEmpty()||edit_nomor_murid.text.toString().isEmpty()
                    || dropdown_value_kelas_murid.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_murid.isEnabled = true
                    btn_simpan_murid.text = "Simpan"

                } else {
                    db.collection("Murid")
                        .whereEqualTo("nomor", edit_nomor_murid.text.toString()).get()
                        .addOnSuccessListener {
                            if (it.size() == 0 || edit_nomor_murid.text.toString() == data.nomor) {
                                if (image_uri != null) {
                                    val builder = StringBuilder()
                                    builder.append(data.nomor + "." + getFileExtension(image_uri!!))

                                    if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension(image_uri!!) == "jpeg") {
                                        storage.child("Murid").child(data.nomor!!).child(builder.toString())
                                            .putFile(image_uri!!).addOnSuccessListener {
                                                storage.child("Murid").child(data.nomor!!)
                                                    .child(builder.toString()).downloadUrl.addOnSuccessListener {

                                                        db.collection("Murid").document(data.nomor!!)
                                                            .update(
                                                                mapOf(
                                                                    "avatar" to it.toString(),
                                                                    "nomor" to edit_nomor_murid.text.toString(),
                                                                    "angkatan" to edit_angkatan_murid.text.toString(),
                                                                    "nama" to edit_nama_murid.text.toString(),
                                                                    "kelas" to dropdown_value_kelas_murid.text.toString(),
                                                                    "ttl" to edit_ttl_murid.text.toString(),
                                                                    "alamat" to edit_alamat_murid.text.toString(),
                                                                    "ayah" to edit_ayah_murid.text.toString(),
                                                                    "ibu" to edit_ibu_murid.text.toString(),
                                                                    "kontak_ayah" to edit_kontak_ayah.text.toString(),
                                                                    "kontak_ibu" to edit_kontak_ibu.text.toString(),
                                                                    "password" to edit_password_murid.text.toString()
                                                                )
                                                            )

                                                        listMurid = Murid(it.toString(),edit_nomor_murid.text.toString(),edit_angkatan_murid.text.toString(),edit_nama_murid.text.toString()
                                                            ,dropdown_value_kelas_murid.text.toString(),edit_ttl_murid.text.toString(),edit_alamat_murid.text.toString()
                                                            ,edit_ayah_murid.text.toString(),edit_ibu_murid.text.toString(),edit_kontak_ayah.text.toString(),edit_kontak_ibu.text.toString(),edit_password_murid.text.toString())


                                                    }.addOnCompleteListener {

                                                        val intent: Intent = Intent(this@EditMuridActivity, DetailMuridActivity::class.java).putExtra("data", listMurid)
                                                        startActivity(intent)
                                                        finish()
                                                        Toast.makeText(this, "Simpan Berhasil",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                            }
                                    } else {
                                        db.collection("Murid").document(data.nomor!!)
                                            .update(
                                                mapOf(
                                                    "avatar" to data.avatar,
                                                    "nomor" to edit_nomor_murid.text.toString(),
                                                    "angkatan" to edit_angkatan_murid.text.toString(),
                                                    "nama" to edit_nama_murid.text.toString(),
                                                    "kelas" to dropdown_value_kelas_murid.text.toString(),
                                                    "ttl" to edit_ttl_murid.text.toString(),
                                                    "alamat" to edit_alamat_murid.text.toString(),
                                                    "ayah" to edit_ayah_murid.text.toString(),
                                                    "ibu" to edit_ibu_murid.text.toString(),
                                                    "kontak_ayah" to edit_kontak_ayah.text.toString(),
                                                    "kontak_ibu" to edit_kontak_ibu.text.toString(),
                                                    "password" to edit_password_murid.text.toString()
                                                )
                                            ).addOnCompleteListener {
                                                listMurid= Murid(data.avatar,edit_nomor_murid.text.toString(),edit_angkatan_murid.text.toString(),edit_nama_murid.text.toString()
                                                    ,dropdown_value_kelas_murid.text.toString(),edit_ttl_murid.text.toString(),edit_alamat_murid.text.toString()
                                                    ,edit_ayah_murid.text.toString(),edit_ibu_murid.text.toString(),edit_kontak_ayah.text.toString(),edit_kontak_ibu.text.toString(),edit_password_murid.text.toString())

                                                val intent: Intent =
                                                    Intent(this@EditMuridActivity, DetailMuridActivity::class.java).putExtra("data", listMurid)
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
                                } else if (image_uri == null) {
                                    db.collection("Murid").document(data.nomor!!)
                                        .update(
                                            mapOf(
                                                "avatar" to "",
                                                "nomor" to edit_nomor_murid.text.toString(),
                                                "angkatan" to edit_angkatan_murid.text.toString(),
                                                "nama" to edit_nama_murid.text.toString(),
                                                "kelas" to dropdown_value_kelas_murid.text.toString(),
                                                "ttl" to edit_ttl_murid.text.toString(),
                                                "alamat" to edit_alamat_murid.text.toString(),
                                                "ayah" to edit_ayah_murid.text.toString(),
                                                "ibu" to edit_ibu_murid.text.toString(),
                                                "kontak_ayah" to edit_kontak_ayah.text.toString(),
                                                "kontak_ibu" to edit_kontak_ibu.text.toString(),
                                                "password" to edit_password_murid.text.toString()
                                            )
                                        ).addOnCompleteListener {
                                            listMurid= Murid("",edit_nomor_murid.text.toString(),edit_angkatan_murid.text.toString(),edit_nama_murid.text.toString()
                                                ,dropdown_value_kelas_murid.text.toString(),edit_ttl_murid.text.toString(),edit_alamat_murid.text.toString()
                                                ,edit_ayah_murid.text.toString(),edit_ibu_murid.text.toString(),edit_kontak_ayah.text.toString(),edit_kontak_ibu.text.toString(),edit_password_murid.text.toString())

                                            val intent: Intent = Intent(this@EditMuridActivity, DetailMuridActivity::class.java).putExtra("data", listMurid)
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
                            }else{
                                Toast.makeText(  this, "Nomor Induk Telah Terpakai", Toast.LENGTH_SHORT).show()
                                btn_simpan_murid.isEnabled = true
                                btn_simpan_murid.text = "Simpan"
                            }
                        }


                }
            }


        } else  if (intent.getStringExtra("jenis") == "TAMBAH_MURID") {
            //assign dropdown kelas
            val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
            val adapter = ArrayAdapter(this, R.layout.dropdown_text, items)
            (dropdown_kelas_murid.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            //intent untuk kembali ke halaman sebelumnya
            img_back_edit_murid.setOnClickListener {
                val intent: Intent = Intent(this@EditMuridActivity, MainActivity::class.java).putExtra("jenis","murid")
                startActivity(intent)
                finish()
            }

            btn_simpan_murid.setOnClickListener {
                btn_simpan_murid.isEnabled = false
                btn_simpan_murid.text = "Loading"

                if (edit_nama_murid.text.toString().isEmpty() || edit_password_murid.text.toString()
                        .isEmpty()
                    || edit_ttl_murid.text.toString().isEmpty() || edit_alamat_murid.text.toString()
                        .isEmpty()
                    || edit_ayah_murid.text.toString().isEmpty() || edit_kontak_ayah.text.toString()
                        .isEmpty()
                    || edit_ibu_murid.text.toString().isEmpty() || edit_kontak_ibu.text.toString()
                        .isEmpty()
                    || edit_angkatan_murid.text.toString()
                        .isEmpty() || edit_nomor_murid.text.toString().isEmpty()
                    || dropdown_value_kelas_murid.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_simpan_murid.isEnabled = true
                    btn_simpan_murid.text = "Simpan"

                } else {
                    db.collection("Murid")
                        .whereEqualTo("nomor", edit_nomor_murid.text.toString()).get()
                        .addOnSuccessListener {
                            if (it.size() == 0) {
                                if (image_uri != null) {
                                    val builder = StringBuilder()
                                    builder.append(edit_nomor_murid.text.toString() + "." + getFileExtension(image_uri!!))
                                    storage.child("Murid").child( edit_nomor_murid.text.toString()).child(builder.toString())
                                        .putFile(image_uri!!)
                                        .addOnSuccessListener {
                                            storage.child("Murid").child(edit_nomor_murid.text.toString()).child(builder.toString()).downloadUrl.addOnSuccessListener {
                                                val murid = hashMapOf(
                                                    "avatar" to it.toString(),
                                                    "nomor" to edit_nomor_murid.text.toString(),
                                                    "angkatan" to edit_angkatan_murid.text.toString(),
                                                    "kelas" to dropdown_value_kelas_murid.text.toString(),
                                                    "nama" to edit_nama_murid.text.toString(),
                                                    "ttl" to edit_ttl_murid.text.toString(),
                                                    "alamat" to edit_alamat_murid.text.toString(),
                                                    "ayah" to edit_ayah_murid.text.toString(),
                                                    "ibu" to edit_ibu_murid.text.toString(),
                                                    "kontak_ayah" to edit_kontak_ayah.text.toString(),
                                                    "kontak_ibu" to edit_kontak_ibu.text.toString(),
                                                    "password" to edit_password_murid.text.toString()
                                                )
                                                listMurid= Murid(it.toString(),edit_nomor_murid.text.toString(),edit_angkatan_murid.text.toString(),edit_nama_murid.text.toString()
                                                    ,dropdown_value_kelas_murid.text.toString(),edit_ttl_murid.text.toString(),edit_alamat_murid.text.toString()
                                                    ,edit_ayah_murid.text.toString(),edit_ibu_murid.text.toString(),edit_kontak_ayah.text.toString(),edit_kontak_ibu.text.toString(),edit_password_murid.text.toString())

                                                db.collection("Murid").document(edit_nomor_murid.text.toString()).set(murid)}.addOnCompleteListener {
                                                listMurid= Murid(it.toString(),edit_nomor_murid.text.toString(),edit_angkatan_murid.text.toString(),edit_nama_murid.text.toString()
                                                    ,dropdown_value_kelas_murid.text.toString(),edit_ttl_murid.text.toString(),edit_alamat_murid.text.toString()
                                                    ,edit_ayah_murid.text.toString(),edit_ibu_murid.text.toString(),edit_kontak_ayah.text.toString(),edit_kontak_ibu.text.toString(),edit_password_murid.text.toString())

                                                val intent: Intent = Intent(this@EditMuridActivity, DetailMuridActivity::class.java).putExtra("data", listMurid)
                                                startActivity(intent)
                                                finish()
                                                Toast.makeText(  this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                                            }

                                        }
                                }else {
                                    val murid = hashMapOf(
                                        "avatar" to "",
                                        "nomor" to edit_nomor_murid.text.toString(),
                                        "angkatan" to edit_angkatan_murid.text.toString(),
                                        "kelas" to dropdown_value_kelas_murid.text.toString(),
                                        "nama" to edit_nama_murid.text.toString(),
                                        "ttl" to edit_ttl_murid.text.toString(),
                                        "alamat" to edit_alamat_murid.text.toString(),
                                        "ayah" to edit_ayah_murid.text.toString(),
                                        "ibu" to edit_ibu_murid.text.toString(),
                                        "kontak_ayah" to edit_kontak_ayah.text.toString(),
                                        "kontak_ibu" to edit_kontak_ibu.text.toString(),
                                        "password" to edit_password_murid.text.toString()
                                    )
                                    db.collection("Murid").document(edit_nomor_murid.text.toString()).set(murid)
                                        .addOnCompleteListener {
                                            listMurid= Murid("",edit_nomor_murid.text.toString(),edit_angkatan_murid.text.toString(),edit_nama_murid.text.toString()
                                                ,dropdown_value_kelas_murid.text.toString(),edit_ttl_murid.text.toString(),edit_alamat_murid.text.toString()
                                                ,edit_ayah_murid.text.toString(),edit_ibu_murid.text.toString(),edit_kontak_ayah.text.toString(),edit_kontak_ibu.text.toString(),edit_password_murid.text.toString())

                                            val intent: Intent = Intent(this@EditMuridActivity, DetailMuridActivity::class.java).putExtra("data", listMurid)
                                            startActivity(intent)
                                            finish()
                                            Toast.makeText(this, "Simpan Berhasil", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(  this, "Nomor Induk Telah Terpakai", Toast.LENGTH_SHORT).show()
                                btn_simpan_murid.isEnabled = true
                                btn_simpan_murid.text = "Simpan"

                            }
                        }

                }
            }
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
            image_uri = data.data!!
            Glide.with(this).load(image_uri).centerCrop().into(img_edit_murid)
        }
    }

}