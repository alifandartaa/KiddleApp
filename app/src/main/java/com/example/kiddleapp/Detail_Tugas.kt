package com.example.kiddleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Hasil_Tugas
import com.example.kiddleapp.model.Model_Hasil_Tugas
import com.example.kiddleapp.model.Model_Tugas
import kotlinx.android.synthetic.main.activity_detail_tugas.*

class Detail_Tugas : AppCompatActivity() {

    //untuk menyimpan Hasil Tugas
    private var detailTugas = ArrayList<Model_Hasil_Tugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas)

    //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Model_Tugas>("data")

        tv_kelas_detail_tugas.text = data.kelas
        tv_judul_detail_tugas.text = data.judul
        tv_tanggal_detail_tugas.text=data.tanggal
        tv_jam_detail_tugas.text=data.jam
        tv_link_detail_tugas.text=data.link

        if(data.gambar!=0)
        {
            img_detail_tugas.setVisibility(View.VISIBLE);
            vv_detail_tugas.setVisibility(View.GONE);
            img_detail_tugas.setImageResource(data.gambar)
        }
        else if(data.video!=0)
        {

            vv_detail_tugas.setVisibility(View.VISIBLE);
            img_detail_tugas.setVisibility(View.GONE);
            vv_detail_tugas.setVideoURI(Uri.parse("android.resource://" +packageName+"/"+data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_tugas.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_tugas)
            vv_detail_tugas.seekTo( 10 );

        }

        if(data.link!=""){
            img_link_detail_tugas.setVisibility(View.VISIBLE)
            tv_link_detail_tugas.setVisibility(View.VISIBLE)

        }

        //recyclerView hasil tugas
        rv_hasil_tugas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        detailTugas.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Hasil_Tugas(R.drawable.avatar, "Lidya", "20 Juli 2020", "20.20 WIB")
        detailTugas.add(temp)

        val temp2 = Model_Hasil_Tugas(R.drawable.avatar, "Lidya", "20 Juli 2020", "20.20 WIB")
        detailTugas.add(temp2)

        rv_hasil_tugas.adapter = Adapter_Hasil_Tugas(detailTugas){
        }

        //intent untuk kembali ke halaman sebelumnya
        img_back_detail_tugas.setOnClickListener {
            onBackPressed()
        }

        // menu
        menu_detail_tugas.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_edit2) {
                    //val intent: Intent = Intent(this@Detail_Tugas, Edit_Tugas::class.java)
                    val intent: Intent = Intent(this@Detail_Tugas, Edit_Tugas::class.java).putExtra("jenis","EDIT_TUGAS")
                    intent.putExtra("data",data)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent = Intent(this@Detail_Tugas, Tugas::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.inflate(R.menu.menu_hapus_edit)
            popup.show()
        }
        tv_link_detail_tugas.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(data.link)
                )
            )
        }

    }
}