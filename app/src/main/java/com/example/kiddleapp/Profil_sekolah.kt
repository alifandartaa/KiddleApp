package com.example.kiddleapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_profil_sekolah.*

class Profil_sekolah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_sekolah)

        menu_profil_sekolah.setOnClickListener {
            val popup:PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_beranda) {
                    val intent: Intent = Intent(this@Profil_sekolah, Edit_Sekolah::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_edit)
            popup.show()
        }

        btn_telepon_sekolah.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:" + tv_kontak_sekolah.text))
            startActivity(intent)
        }
    }
}