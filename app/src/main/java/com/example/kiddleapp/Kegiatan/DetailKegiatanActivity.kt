package com.example.kiddleapp.Kegiatan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_detail_kegiatan.*


class DetailKegiatanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kegiatan)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Kegiatan>("data")

        tv_judul_detail_kegiatan.text = data.judul
        tv_tanggal_detail_kegiatan.text = data.tanggal
        tv_isi_detail_kegiatan.text=data.isi
        tv_link_detail_kegiatan.text=data.link

        if(data.gambar!=0) {
            img_detail_kegiatan.visibility = View.VISIBLE
            vv_detail_kegiatan.visibility = View.GONE
            img_detail_kegiatan.setImageResource(data.gambar)
        }
        else if(data.video!=0) {
            vv_detail_kegiatan.visibility = View.VISIBLE
            img_detail_kegiatan.visibility = View.GONE
            vv_detail_kegiatan.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_kegiatan.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_kegiatan)
            vv_detail_kegiatan.seekTo(10)

        }

        if(data.link!="") {
            img_link_detail_kegiatan.visibility = View.VISIBLE
            tv_link_detail_kegiatan.visibility = View.VISIBLE

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_kegiatan.setOnClickListener {
            onBackPressed()
        }

        // menu
        menu_detail_kegiatan.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    val intent: Intent = Intent(
                        this@DetailKegiatanActivity,
                        EditKegiatanActivity::class.java
                    ).putExtra("data", data)
                    intent.putExtra("jenis","EDIT_KEGIATAN")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent =
                        Intent(this@DetailKegiatanActivity, KegiatanActivity::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_hapus_edit)
            popup.show()
        }

        tv_link_detail_kegiatan.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(data.link)
                )
            )
        }
    }
}