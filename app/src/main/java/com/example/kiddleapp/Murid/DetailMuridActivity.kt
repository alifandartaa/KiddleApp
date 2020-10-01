package com.example.kiddleapp.Murid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.MainActivity
import com.example.kiddleapp.Murid.Model.Murid
import com.example.kiddleapp.R
import com.example.kiddleapp.Rapor.EditRaporActivity
import com.example.kiddleapp.RekapPresensi.RekapPresensiActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail__murid.*
import kotlinx.android.synthetic.main.activity_detail_jurnal.*

class DetailMuridActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__murid)

        //mendapatkan data dari intent
        val data = intent.getParcelableExtra<Murid>("data")

        img_back_murid.setOnClickListener {
            val intent: Intent =  Intent(this@DetailMuridActivity, MainActivity::class.java  )
            startActivity(intent)
        }

        //isi value dari halaman sebelumnya
        Glide.with(this).load(data.avatar).centerCrop().into(img_avatar_detail_murid)
        tv_murid_nama.text = data.nama
        tv_murid_nomor.text = data.nomor
        tv_murid_kelas.text = data.kelas
        tv_murid_ttl.text = data.ttl
        tv_murid_alamat.text = data.alamat
        tv_murid_ayah.text = data.ayah
        tv_kontak_ayah.text = data.kontakAyah
        tv_murid_ibu.text = data.ibu
        tv_kontak_ibu.text = data.kontakIbu
        tv_murid_password.text = data.password

        //intent untuk manggil ortu
        btn_telepon_ayah.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_ayah.text)
            startActivity(intent)
        }
        btn_telepon_ibu.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_ibu.text)
            startActivity(intent)
        }

        //buat nampilin menu
        menu_murid.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit) {
                    val intent = Intent(this@DetailMuridActivity, EditMuridActivity::class.java).putExtra("data", data)
                    intent.putExtra("jenis","EDIT_MURID")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                } else if(it.itemId == R.id.menu_hapus) {
                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Hapus Murid")
                        setMessage(
                            "Apa anda yakin ingin menghapus murid ini?"
                        )
                        setPositiveButton("Ya") { _, _ ->
                            db.collection("Murid").document(data.nomor!!).delete().addOnSuccessListener {
                                    storage.child("Murid/"+data.nomor!!+"/"+data.nomor!!+".jpg").delete().addOnSuccessListener {

                                    }
                                    storage.child("Murid/"+data.nomor!!+"/"+data.nomor!!+".jpeg").delete().addOnSuccessListener {

                                    }
                                    storage.child("Murid/"+data.nomor!!+"/"+data.nomor!!+".png").delete().addOnSuccessListener {

                                    }

                                    val intent: Intent =
                                        Intent(this@DetailMuridActivity, EditMuridActivity::class.java)
                                    startActivity(intent)
                                    Toast.makeText(
                                        context,
                                        "Murid Berhasil di Hapus",
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

        //intent ke halaman rekap presensi
        btn_rekap_presensi.setOnClickListener {
            val intent: Intent = Intent(this@DetailMuridActivity, RekapPresensiActivity::class.java)
            startActivity(intent)
        }

        //intent ke halaman rapor
        btn_lihat_rapor.setOnClickListener {
            val intent: Intent =
                Intent(this@DetailMuridActivity, RekapRaporActivity::class.java).putExtra(
                    "data",
                    data
                )
            startActivity(intent)
        }
    }
}