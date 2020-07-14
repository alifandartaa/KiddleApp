package com.example.kiddleapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import com.example.kiddleapp.model.Model_Murid
import kotlinx.android.synthetic.main.activity_detail__murid.*
import kotlinx.android.synthetic.main.activity_profil.*

class Detail_Murid : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__murid)

        //mendapatkan data dari intent
        val data = intent.getParcelableExtra<Model_Murid>("data")

        img_back_murid.setOnClickListener {
            onBackPressed()
        }

        //isi value dari halaman sebelumnya
        img_avatar_detail_murid.setImageResource(data.avatar)
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
            intent.setData(Uri.parse("tel:" + tv_kontak_ayah.text))
            startActivity(intent)
        }
        btn_telepon_ibu.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:" + tv_kontak_ibu.text))
            startActivity(intent)
        }

        //buat nampilin menu
        menu_murid.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit) {
                    Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                } else if(it.itemId == R.id.menu_hapus) {
                    Toast.makeText(this, "Hapus", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_edit_hapus)
            popup.show()
        }

        //intent ke halaman rekap presensi
        btn_rekap_presensi.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        //intent ke halaman rapor
        btn_lihat_rapor.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}