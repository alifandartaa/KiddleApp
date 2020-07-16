package com.example.kiddleapp

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.model.Model_Jurnal
import com.example.kiddleapp.model.Model_Kegiatan

import kotlinx.android.synthetic.main.activity_detail_jurnal.*
import kotlinx.android.synthetic.main.activity_detail_kegiatan.*


class Detail_Kegiatan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kegiatan)

    //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Model_Kegiatan>("data")

        tv_judul_detail_kegiatan.text = data.judul
        tv_tanggal_detail_kegiatan.text=data.tanggal
        tv_isi_detail_kegiatan.text=data.isi

        if(data.gambar!=0)
        {
            img_detail_kegiatan.setVisibility(View.VISIBLE);
            vv_detail_kegiatan.setVisibility(View.GONE);
            img_detail_kegiatan.setImageResource(data.gambar)
        }
        else if(data.video!=0)
        {
            vv_detail_kegiatan.setVisibility(View.VISIBLE);
            img_detail_kegiatan.setVisibility(View.GONE);
            vv_detail_kegiatan.setVideoURI(Uri.parse("android.resource://" +packageName+"/"+data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_kegiatan.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_kegiatan)
            vv_detail_kegiatan.seekTo( 10 );

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
                    val intent: Intent = Intent(this@Detail_Kegiatan, Edit_Kegiatan::class.java).putExtra("data",data)
                    intent.putExtra("jenis","EDIT_KEGIATAN")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent = Intent(this@Detail_Kegiatan, Kegiatan::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_hapus_edit)
            popup.show()
        }
    }
}