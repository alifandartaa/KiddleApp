package com.example.kiddleapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Komentar
import com.example.kiddleapp.model.Model_Komentar
import com.example.kiddleapp.model.Model_Pengumuman
import kotlinx.android.synthetic.main.activity_detail_pengumuman.*
import kotlinx.android.synthetic.main.activity_notifikasi.*

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

        //komentar
        btn_komentar.setOnClickListener {v ->
            //mengkosongkan isi arraylist
            val temp = Model_Komentar(R.drawable.avatar, "Lidya", "", tv_komentar.text.toString())
            komentar.add(komentar.size, temp);
            rv_komentar.adapter = Adapter_Komentar(komentar){
            }
            tv_komentar.setText("");
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)

        }


    }

}