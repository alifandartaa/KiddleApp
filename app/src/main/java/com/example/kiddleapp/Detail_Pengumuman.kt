package com.example.kiddleapp

import android.app.ActionBar
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Hasil_Tugas
import com.example.kiddleapp.adapter.Adapter_Kegiatan
import com.example.kiddleapp.adapter.Adapter_Komentar
import com.example.kiddleapp.adapter.Adapter_Tugas
import com.example.kiddleapp.model.Model_Hasil_Tugas
import com.example.kiddleapp.model.Model_Komentar
import com.example.kiddleapp.model.Model_Pengumuman
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.activity_detail_kegiatan.*
import kotlinx.android.synthetic.main.activity_detail_pengumuman.*
import kotlinx.android.synthetic.main.activity_detail_tugas.*
import kotlinx.android.synthetic.main.activity_profil_sekolah.*
import kotlinx.android.synthetic.main.activity_tugas.*

class Detail_Pengumuman : AppCompatActivity() {

    //untuk menyimpan Hasil Tugas
    private var komentar = ArrayList<Model_Komentar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pengumuman)

    //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Model_Pengumuman>("data")

        tv_judul_detail_pengumuman.text = data.judul
        tv_tanggal_detail_pengumuman.text=data.tanggal
        tv_isi_detail_pengumuman.text=data.isi
        if(data.gambar!=0)
        {
            img_detail_pengumuman.setVisibility(View.VISIBLE);
            vv_detail_pengumuman.setVisibility(View.GONE);
            img_detail_pengumuman.setImageResource(data.gambar)
        }
        else if(data.video!=0)
        {

            vv_detail_pengumuman.setVisibility(View.VISIBLE);
            img_detail_pengumuman.setVisibility(View.GONE);
            vv_detail_pengumuman.setVideoURI(Uri.parse("android.resource://" +packageName+"/"+data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_pengumuman.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_pengumuman)
            vv_detail_pengumuman.seekTo( 10 );

        }

        //recyclerView hasil tugas
        rv_komentar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        komentar.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Komentar(R.drawable.avatar, "Lidya", "Kepala Sekolah", "jaga kesehatan")
        komentar.add(temp)

        val temp2 = Model_Komentar(R.drawable.avatar, "Lidya", "", "iaya SPP bagaimana ?")
        komentar.add(temp2)

        rv_komentar.adapter = Adapter_Komentar(komentar){
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_pengumuman.setOnClickListener {
            onBackPressed()
        }

        // menu
        menu_detail_pengumuman.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    //val intent: Intent = Intent(this@Detail_Tugas, Edit_Tugas::class.java)
                    val intent: Intent = Intent(this@Detail_Pengumuman, Edit_Pengumuman::class.java).putExtra("jenis","EDIT_PENGUMUMAN")
                    intent.putExtra("data",data)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent = Intent(this@Detail_Pengumuman, Pengumuman::class.java)
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