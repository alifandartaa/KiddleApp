package com.example.kiddleapp.Profil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Sekolah.EditSekolahActivity
import kotlinx.android.synthetic.main.activity_profil_sekolah.*

class ProfilSekolahActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_sekolah)

        //buat nampilin menu
        menu_profil_sekolah.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_edit) {
                    val intent: Intent =
                        Intent(this@ProfilSekolahActivity, EditSekolahActivity::class.java)

                    //agar saat edit sudah terisi. bisa diganti dengan cara lain yang lebih efektif kayaknya. kurang gambar sekolah
                    intent.putExtra("nama_sekolah", tv_nama_sekolah.text)
                    intent.putExtra("alamat_sekolah", tv_alamat_sekolah.text)
                    intent.putExtra("kontak_sekolah", tv_kontak_sekolah.text)
                    intent.putExtra("deskripsi_sekolah", tv_deskripsi_sekolah.text)
                    intent.putExtra("visi_sekolah", tv_visi_sekolah.text)
                    intent.putExtra("misi_sekolah", tv_misi_sekolah.text)
                    intent.putExtra("fasilitas_sekolah", tv_fasilitas_sekolah.text)
                    intent.putExtra("prestasi_sekolah", tv_prestasi_sekolah.text)

                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_edit_only)
            popup.show()
        }

        //intent buat manggil telepon
        btn_telepon_sekolah.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + tv_kontak_sekolah.text)
            startActivity(intent)
        }
    }
}