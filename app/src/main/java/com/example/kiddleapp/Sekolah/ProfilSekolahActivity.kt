package com.example.kiddleapp.Sekolah

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Beranda.BerandaFragment
import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.Profil.EditProfilActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Sekolah.Model.Sekolah
import com.example.kiddleapp.Tugas.EditTugasActivity
import com.example.kiddleapp.Tugas.Model.Tugas
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail_tugas.*
import kotlinx.android.synthetic.main.activity_edit__sekolah.*
import kotlinx.android.synthetic.main.activity_guru.*
import kotlinx.android.synthetic.main.activity_profil_sekolah.*

class ProfilSekolahActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference
  //  private lateinit var data: Sekolah
    var id_sekolah : String? = null
    var gambar :String? = null
    lateinit var data: Sekolah

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_sekolah)

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)


        val sekolah = db.collection("Profil Sekolah").document("LBbnMxieBzdWuxvOMGbw")
        sekolah.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    data = Sekolah(
                        document.id,
                        document.getString("nama"),
                                document.getString("alamat"),
                                document.getString("kontak"),
                                document.getString("isi"),
                                document.getString("visi"),
                                document.getString("misi"),
                                document.getString("fasilitas"),
                                document.getString("prestasi"),
                                document.getString("banner")

                    )
                    id_sekolah= document.getString("id_sekolah").toString()
                    gambar = document.getString("banner").toString()
                    tv_nama_sekolah.text = document.getString("nama")
                    tv_alamat_sekolah.text = document.getString("alamat")
                    tv_kontak_sekolah.text = document.getString("kontak")
                    tv_deskripsi_sekolah.text = document.getString("isi")
                    tv_visi_sekolah.text =document.getString("visi")
                    tv_misi_sekolah.text =document.getString("misi")
                    tv_fasilitas_sekolah.text = document.getString("fasilitas")
                    tv_prestasi_sekolah.text =document.getString("prestasi")
                    Glide.with(this).load(document.getString("banner")).centerCrop().into(img_profil_sekolah)
                    //      Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    //   Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "get failed with ", exception)
            }

        //buat nampilin menu
        //Agar tombol menu hanya bisa dilihat admin
        if(sharedPreferences.getString("kelas","") == "admin"){
            menu_profil_sekolah.visibility= View.VISIBLE
            menu_profil_sekolah.setOnClickListener {
                val popup: PopupMenu = PopupMenu(this, it)
                popup.setOnMenuItemClickListener {
                    if (it.itemId == R.id.menu_edit) {
                        val intent: Intent =
                            Intent(this@ProfilSekolahActivity, EditSekolahActivity::class.java)
                        intent.putExtra("data", data)
                        startActivity(intent)
                        finish()
                        // startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    return@setOnMenuItemClickListener false
                }
                popup.inflate(R.menu.menu_edit_only)
                popup.show()
            }

        }

        //intent buat manggil telepon
        btn_telepon_sekolah.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_sekolah.text)
            startActivity(intent)
        }

        img_back_profil_sekolah.setOnClickListener {
            super.onBackPressed()
        }
    }
}