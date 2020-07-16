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
import com.example.kiddleapp.model.Model_Materi

import kotlinx.android.synthetic.main.activity_detail_jurnal.*
import kotlinx.android.synthetic.main.activity_detail_kegiatan.*
import kotlinx.android.synthetic.main.activity_detail_materi.*


class Detail_Materi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)

    //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Model_Materi>("data")

        tv_judul_detail_materi.text = data.judul
        tv_tanggal_detail_materi.text=data.tanggal
        tv_isi_detail_materi.text=data.isi

        if(data.gambar!=0)
        {
            img_detail_materi.setVisibility(View.VISIBLE);
            vv_detail_materi.setVisibility(View.GONE);
            img_detail_materi.setImageResource(data.gambar)
        }
        else if(data.video!=0)
        {
            vv_detail_materi.setVisibility(View.VISIBLE);
            img_detail_materi.setVisibility(View.GONE);
            vv_detail_materi.setVideoURI(Uri.parse("android.resource://" +packageName+"/"+data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_materi.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_materi)
            vv_detail_materi.seekTo( 10 );

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_materi.setOnClickListener {
            onBackPressed()
        }

        // menu
        menu_detail_materi.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    val intent: Intent = Intent(this@Detail_Materi, Edit_Materi::class.java).putExtra("data",data)
                    intent.putExtra("jenis","EDIT_MATERI")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent = Intent(this@Detail_Materi, Materi::class.java)
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