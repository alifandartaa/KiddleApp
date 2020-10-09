package com.example.kiddleapp.Sekolah

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.R
import com.example.kiddleapp.Sekolah.Model.Sekolah
import com.example.kiddleapp.Tugas.Model.Tugas
import com.example.kiddleapp.Tugas.TugasActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit__sekolah.*
import kotlinx.android.synthetic.main.activity_edit_pengumuman.*
import kotlinx.android.synthetic.main.activity_edit_tugas.*
import kotlinx.android.synthetic.main.activity_profil_sekolah.*

class EditSekolahActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var image_uri: Uri? = null

    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__sekolah)
        val data = intent.getParcelableExtra<Sekolah>("data")
        edit_nama_sekolah.setText(data.nama)
        edit_alamat_sekolah.setText(data.alamat)
        edit_kontak_sekolah.setText(data.kontak)
        edit_deskripsi_sekolah.setText(data.isi)
        edit_visi_sekolah.setText(data.visi)
        edit_misi_sekolah.setText(data.misi)
        edit_prestasi_sekolah.setText(data.prestasi)
        edit_fasilitas_sekolah.setText(data.prestasi)
        Glide.with(this).load(data.gambar).centerCrop().into(img_edit_foto_sekolah)
        image_uri = Uri.parse(data.gambar)

        //barangkali bingung mau edit apa jadi di assign dulu value nya. kurang gambar sekolah nya
//        edit_nama_sekolah.setText(intent.getStringExtra("nama_sekolah"))
//        edit_alamat_sekolah.setText(intent.getStringExtra("alamat_sekolah"))
//        edit_kontak_sekolah.setText(intent.getStringExtra("kontak_sekolah"))
//        edit_deskripsi_sekolah.setText(intent.getStringExtra("deskripsi_sekolah"))
//        edit_visi_sekolah.setText(intent.getStringExtra("visi_sekolah"))
//        edit_misi_sekolah.setText(intent.getStringExtra("misi_sekolah"))
//        edit_prestasi_sekolah.setText(intent.getStringExtra("prestasi_sekolah"))
//        edit_fasilitas_sekolah.setText(intent.getStringExtra("fasilitas_sekolah"))
//        Glide.with(this).load(intent.getStringExtra("gambar")).centerCrop().into(img_edit_foto_sekolah)

        // intent untuk kembali ke halaman sebelumnya
        img_back_edit_sekolah.setOnClickListener {
            val intent: Intent = Intent(this@EditSekolahActivity, ProfilSekolahActivity::class.java)
            startActivity(intent)
            finish()
        }

        //ganti pake firebase untuk menyimpan perubahan
        btn_simpan_sekolah.setOnClickListener {
            btn_simpan_sekolah.isEnabled = false
            btn_simpan_sekolah.text = "Loading"

            if(edit_nama_sekolah.text.toString().isEmpty() || edit_alamat_sekolah.text.toString().isEmpty()
                ||edit_kontak_sekolah.text.toString().isEmpty() ||edit_deskripsi_sekolah.text.toString().isEmpty()
                ||edit_visi_sekolah.text.toString().isEmpty() || edit_misi_sekolah.text.toString().isEmpty()
                ||edit_prestasi_sekolah.text.toString().isEmpty()|| edit_fasilitas_sekolah.text.toString().isEmpty()) {
                    Toast.makeText(
                        this,
                        "Semua Kolom Harus Diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                btn_simpan_sekolah.isEnabled = true
                btn_simpan_sekolah.text = "Simpan"

                }else {
                if (getFileExtension(image_uri!!) == "jpg" || getFileExtension(image_uri!!) == "png" || getFileExtension( image_uri!! ) == "jpeg") {
                    val builder = StringBuilder()
                    builder.append(data.id_sekolah + "." + getFileExtension(image_uri!!))
                    storage.child("Profil Sekolah").child(data.id_sekolah!!)
                        .child(builder.toString()).putFile(image_uri!!).addOnSuccessListener {
                        storage.child("Profil Sekolah").child(data.id_sekolah!!)
                            .child(builder.toString()).downloadUrl.addOnSuccessListener {
                            db.collection("Profil Sekolah").document(data.id_sekolah!!)
                                .update(
                                    mapOf(
                                        "nama" to edit_nama_sekolah.text.toString(),
                                        "alamat" to edit_alamat_sekolah.text.toString(),
                                        "kontak" to edit_kontak_sekolah.text.toString(),
                                        "isi" to edit_deskripsi_sekolah.text.toString(),
                                        "visi" to edit_visi_sekolah.text.toString(),
                                        "misi" to edit_misi_sekolah.text.toString(),
                                        "banner" to it.toString(),
                                        "fasilitas" to edit_fasilitas_sekolah.text.toString(),
                                        "prestasi" to edit_prestasi_sekolah.text.toString()
                                    )
                                )
                        }.addOnCompleteListener {
                            val intent: Intent =
                                Intent(this@EditSekolahActivity, ProfilSekolahActivity::class.java)
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
                    db.collection("Profil Sekolah").document(data.id_sekolah!!)
                        .update(
                            mapOf(
                                "nama" to edit_nama_sekolah.text.toString(),
                                "alamat" to edit_alamat_sekolah.text.toString(),
                                "kontak" to edit_kontak_sekolah.text.toString(),
                                "isi" to edit_deskripsi_sekolah.text.toString(),
                                "visi" to edit_visi_sekolah.text.toString(),
                                "misi" to edit_misi_sekolah.text.toString(),
                                "banner" to data.gambar,
                                "fasilitas" to edit_fasilitas_sekolah.text.toString(),
                                "prestasi" to edit_prestasi_sekolah.text.toString()
                            )
                        )
                    val intent: Intent = Intent(this@EditSekolahActivity, ProfilSekolahActivity::class.java)
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
            image_uri = data?.data
            Glide.with(this).load(image_uri).centerCrop().into(img_edit_foto_sekolah)
        }
    }
}