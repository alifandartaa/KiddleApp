package com.example.kiddleapp.Profil

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Guru.GuruActivity
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Sekolah.Model.Sekolah
import com.example.kiddleapp.Sekolah.ProfilSekolahActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_notifikasi.*
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {
//lateinit var avatar:String
lateinit var  guru : Guru
    var storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        //lihat profil siapa? diri sendiri apa guru
        var tipeAkses: String = intent.getStringExtra("tipeAkses")

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()


        if(tipeAkses == "PROFIL") {
            db.document("Guru/${sharedPreferences.getString("id_guru", "")}").get().addOnSuccessListener {
                guru = Guru(
                    it.getString("avatar"),
                    it.getString("nama"),
                    it.getString("nomor"),
                    it.getString("kontak"),
                    it.getString("jabatan"),
                    it.getString("password")
                    )
                tv_nama_profil.text = it.getString("nama")
                tv_nomor_profil.text = it.getString("nomor")
                tv_kontak_profil.text = it.getString("kontak")
                tv_jabatan_profil.text = it.getString("jabatan")
                tv_password_profil.text = it.getString("password")
                Glide.with(this).load(it.getString("avatar")).centerCrop().into(img_profil)
            }

            img_back_profil.setOnClickListener {
                onBackPressed()
            }

            //buat nampilin menu
            menu_profil.visibility= View.VISIBLE
            menu_profil.setOnClickListener {
                val popup:PopupMenu = PopupMenu(this, it)
                popup.setOnMenuItemClickListener {
                    if(it.itemId == R.id.menu_edit) {
                        val intent: Intent = Intent(this@ProfilActivity, EditProfilActivity::class.java)
                        intent.putExtra("data", guru)

                        //assign value ke editView nanti. kurang gambar
                        intent.putExtra("jenis", "EDIT_PROFIL")
                        startActivity(intent)
                        finish()

                        return@setOnMenuItemClickListener true
                    }
                    return@setOnMenuItemClickListener false
                }
                popup.inflate(R.menu.menu_edit_only)
                popup.show()
            }

        } else if(tipeAkses == "GURU") {
           val guru = intent.getParcelableExtra<Guru>("data")
            //mengambil data dari halaman sebelumnya
            tv_nama_profil.text = guru.nama
            tv_nomor_profil.text = guru.nomor
            tv_kontak_profil.text = guru.kontak
            tv_jabatan_profil.text = guru.jabatan
            tv_password_profil.text = guru.password
            Glide.with(this).load(guru.avatar).centerCrop().into(img_profil)
            //avatar = data.avatar.toString()
            //Agar admin bisa nampilin menu

            img_back_profil.setOnClickListener {
                val intent: Intent = Intent(this@ProfilActivity, GuruActivity::class.java)
                startActivity(intent)
            }

            if(sharedPreferences.getString("kelas","") == "admin"){
                menu_profil.visibility= View.VISIBLE
                menu_profil.setOnClickListener {
                    val popup:PopupMenu = PopupMenu(this, it)
                    popup.setOnMenuItemClickListener {
                        if(it.itemId == R.id.menu_edit) {
                            val intent: Intent = Intent(this@ProfilActivity, EditProfilActivity::class.java)
                            intent.putExtra("data", guru)
                            //assign value ke editView nanti. kurang gambar
                            intent.putExtra("jenis", "EDIT_GURU")
                            startActivity(intent)
                            finish()

                            return@setOnMenuItemClickListener true
                        } else if(it.itemId == R.id.menu_hapus) {
                            MaterialAlertDialogBuilder(this).apply {
                                setTitle("Hapus Guru")
                                setMessage(
                                    "Apa anda yakin ingin menghapus data guru ini?"
                                )
                                setPositiveButton("Ya") { _, _ ->
                                    db.collection("Guru").document(guru.nomor!!).delete()
                                        .addOnSuccessListener {
                                            storage.child("Guru/"+guru.nomor!!+"/"+guru.nomor!!+".jpg").delete().addOnSuccessListener {

                                            }
                                            storage.child("Guru/"+guru.nomor!!+"/"+guru.nomor!!+".jpeg").delete().addOnSuccessListener {

                                            }
                                            storage.child("Guru/"+guru.nomor!!+"/"+guru.nomor!!+".png").delete().addOnSuccessListener {

                                            }

                                            val intent: Intent =
                                                Intent(this@ProfilActivity, GuruActivity::class.java)
                                            startActivity(intent)
                                            Toast.makeText(
                                                context,
                                                "Guru Berhasil di Hapus",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    setNegativeButton("Tidak") { _, _ -> }
                                }.show()
                            }
                        }
                        return@setOnMenuItemClickListener false
                    }
                    popup.inflate(R.menu.menu_edit_hapus)
                    popup.show()
                }
            }

        }

        //intent untuk memanggil telepon
        btn_telepon_profil.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_profil.text)
            startActivity(intent)
        }
    }
}