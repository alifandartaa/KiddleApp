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
import com.example.kiddleapp.model.Model_Parenting

import kotlinx.android.synthetic.main.activity_detail_jurnal.*
import kotlinx.android.synthetic.main.activity_detail_kegiatan.*
import kotlinx.android.synthetic.main.activity_detail_materi.*
import kotlinx.android.synthetic.main.activity_detail_parenting.*


class Detail_Parenting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_parenting)

    //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Model_Parenting>("data")

        tv_judul_detail_parenting.text = data.judul
        tv_tanggal_detail_parenting.text=data.tanggal
        tv_isi_detail_parenting.text=data.isi

        if(data.gambar!=0)
        {
            img_detail_parenting.setVisibility(View.VISIBLE);
            vv_detail_parenting.setVisibility(View.GONE);
            img_detail_parenting.setImageResource(data.gambar)
        }
        else if(data.video!=0)
        {
            vv_detail_parenting.setVisibility(View.VISIBLE);
            img_detail_parenting.setVisibility(View.GONE);
            vv_detail_parenting.setVideoURI(Uri.parse("android.resource://" +packageName+"/"+data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_parenting.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_parenting)
            vv_detail_parenting.seekTo( 10 );

        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_parenting.setOnClickListener {
            onBackPressed()
        }

        // menu
        menu_detail_parenting.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    val intent: Intent = Intent(this@Detail_Parenting, Edit_Parenting::class.java).putExtra("data",data)
                    intent.putExtra("jenis","EDIT_PARENTING")
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent = Intent(this@Detail_Parenting, Parenting::class.java)
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