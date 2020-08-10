package com.example.kiddleapp.Tugas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Adapter.HasilTugasAdapter
import com.example.kiddleapp.Tugas.Model.HasilTugas
import com.example.kiddleapp.Tugas.Model.Tugas
import kotlinx.android.synthetic.main.activity_detail_tugas.*

class DetailTugasActivity : AppCompatActivity() {

    //untuk menyimpan Hasil TugasActivity
    private var detailTugas = ArrayList<HasilTugas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Tugas>("data")

        tv_kelas_detail_tugas.text = data.kelas
        tv_judul_detail_tugas.text = data.judul
        tv_isi_detail_tugas.text = data.isi
        tv_tanggal_detail_tugas.text=data.tanggal
        tv_jam_detail_tugas.text=data.jam
        tv_link_detail_tugas.text=data.link

        if(data.gambar!= "") {
            img_detail_tugas.visibility = View.VISIBLE
            vv_detail_tugas.visibility = View.GONE
            Glide.with(this).load(data.gambar).centerCrop().into(img_detail_tugas)
            //img_detail_tugas.setImageResource(data.gambar)
        }
        else if(data.video!="") {
            vv_detail_tugas.visibility = View.VISIBLE
            img_detail_tugas.visibility = View.GONE
            vv_detail_tugas.setVideoURI(Uri.parse( data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_tugas.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_tugas)
            vv_detail_tugas.seekTo(10)

        }

        if(data.link!="") {
            img_link_detail_tugas.visibility = View.VISIBLE
            tv_link_detail_tugas.visibility = View.VISIBLE

        }

        //recyclerView hasil tugas
        rv_hasil_tugas.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        detailTugas.clear()

        //bisa diganti dengan data dari firebase
        val temp = HasilTugas(
            R.drawable.avatar,
            "Lidya",
            "20 Juli 2020",
            "20.20 WIB"
        )
        detailTugas.add(temp)

        val temp2 = HasilTugas(
            R.drawable.avatar,
            "Lidya",
            "20 Juli 2020",
            "20.20 WIB"
        )
        detailTugas.add(temp2)

        rv_hasil_tugas.adapter =
            HasilTugasAdapter(detailTugas) {
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
                    //val intent: Intent = Intent(this@DetailTugasActivity, EditTugasActivity::class.java)
                    val intent: Intent = Intent(
                        this@DetailTugasActivity,
                        EditTugasActivity::class.java
                    ).putExtra("jenis", "EDIT_TUGAS")
                    intent.putExtra("data", data)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent = Intent(this@DetailTugasActivity, TugasActivity::class.java)
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