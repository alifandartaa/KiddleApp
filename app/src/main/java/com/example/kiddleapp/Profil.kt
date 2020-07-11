package com.example.kiddleapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_profil.*

class Profil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        //lihat profil siapa? diri sendiri apa guru
        var tipeAkses:String = intent.getStringExtra("tipeAkses")

        //buat nampilin menu
        menu_profil.setOnClickListener {
            val popup:PopupMenu = PopupMenu(this, it)
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
            //untuk memilih apakah bisa hapus atau tidak
            if(tipeAkses == "PROFIL"){
                popup.inflate(R.menu.menu_edit)
            } else  {
                popup.inflate(R.menu.menu_edit_hapus)
            }

            popup.show()
        }

        //intent untuk memanggil telepon
        btn_telepon_profil.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:" + tv_kontak_profil.text))
            startActivity(intent)
        }
    }
}