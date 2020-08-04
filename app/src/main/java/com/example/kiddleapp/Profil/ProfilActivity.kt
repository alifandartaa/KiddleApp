package com.example.kiddleapp.Profil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        //lihat profil siapa? diri sendiri apa guru
        var tipeAkses: String = intent.getStringExtra("tipeAkses")

        img_back_profil.setOnClickListener {
            onBackPressed()
        }

        if(tipeAkses == "PROFIL") {
            //assign textView dan imageVies dari database sesuai profil sendiri
        } else {
            //mengambil data dari halaman sebelumnya
            val data = intent.getParcelableExtra<Guru>("data")
            tv_nama_profil.text = data.nama
            tv_nomor_profil.text = data.nomor
            tv_kontak_profil.text = data.kontak
            tv_jabatan_profil.text = data.jabatan
            tv_password_profil.text = data.password
            img_profil.setImageResource(data.avatar)
        }

        //buat nampilin menu
        menu_profil.setOnClickListener {
            val popup:PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit) {
                    val intent: Intent = Intent(this@ProfilActivity, EditProfilActivity::class.java)

                    //assign value ke editView nanti. kurang gambar
                    intent.putExtra("jenis", "EDIT_PROFIL")
                    intent.putExtra("nama_profil", tv_nama_profil.text)
                    intent.putExtra("nomor_profil", tv_nomor_profil.text)
                    intent.putExtra("kontak_profil", tv_kontak_profil.text)
                    intent.putExtra("jabatan_profil", tv_jabatan_profil.text)
                    intent.putExtra("password_profil", tv_password_profil.text)
                    startActivity(intent)

                    return@setOnMenuItemClickListener true
                } else if(it.itemId == R.id.menu_hapus) {
                    Toast.makeText(this, "Hapus", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            //untuk memilih apakah bisa hapus atau tidak
            if(tipeAkses == "PROFIL"){
                popup.inflate(R.menu.menu_edit_only)
            } else {
                popup.inflate(R.menu.menu_edit_hapus)
            }

            popup.show()
        }

        //intent untuk memanggil telepon
        btn_telepon_profil.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_profil.text)
            startActivity(intent)
        }
    }
}