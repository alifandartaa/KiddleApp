package com.example.kiddleapp.Pengumuman

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.MediaController
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kiddleapp.Komentar.Komentar
import com.example.kiddleapp.Komentar.KomentarAdapter
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_detail_pengumuman.*

class DetailPengumumanActivity : AppCompatActivity() {

    //untuk menyimpan Hasil TugasActivity
    private var komentar = ArrayList<Komentar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pengumuman)

        //mengambil data dari halaman sebelumnya
        val data = intent.getParcelableExtra<Pengumuman>("data")

        tv_judul_detail_pengumuman.text = data.judul
        tv_tanggal_detail_pengumuman.text = data.tanggal
        tv_isi_detail_pengumuman.text=data.isi
        if(!data.gambar.isNullOrEmpty()) {
            img_detail_pengumuman.visibility = View.VISIBLE
            vv_detail_pengumuman.visibility = View.GONE
            Glide.with(this).load(data.gambar).centerCrop().into(img_detail_pengumuman)
        }
        else if(!data.video.isNullOrEmpty()) {

            vv_detail_pengumuman.visibility = View.VISIBLE
            img_detail_pengumuman.visibility = View.GONE
            vv_detail_pengumuman.setVideoURI(Uri.parse(data.video))
            var media_Controller: MediaController = MediaController(this)
            vv_detail_pengumuman.setMediaController(media_Controller)
            media_Controller.setAnchorView(vv_detail_pengumuman)
            vv_detail_pengumuman.seekTo(10)

        }

        //recyclerView hasil tugas
        rv_komentar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        komentar.clear()

        //bisa diganti dengan data dari firebase
        val temp = Komentar(
            R.drawable.avatar,
            "Lidya",
            "Kepala Sekolah",
            "jaga kesehatan"
        )
        komentar.add(temp)

        val temp2 = Komentar(
            R.drawable.avatar,
            "Lidya",
            "",
            "iaya SPP bagaimana ?"
        )
        komentar.add(temp2)

        rv_komentar.adapter =
            KomentarAdapter(komentar) {
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
                    //val intent: Intent = Intent(this@DetailTugasActivity, EditTugasActivity::class.java)
                    val intent: Intent = Intent(
                        this@DetailPengumumanActivity,
                        EditPengumumanActivity::class.java
                    ).putExtra("jenis", "EDIT_PENGUMUMAN")
                    intent.putExtra("data", data)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
               else if(it.itemId == R.id.menu_hapus2) {
                    //belum ditambah buat ngehapus
                    val intent: Intent =
                        Intent(this@DetailPengumumanActivity, PengumumanActivity::class.java)
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
            val temp = Komentar(
                R.drawable.avatar,
                "Lidya",
                "",
                tv_komentar.text.toString()
            )
            komentar.add(komentar.size, temp)
            rv_komentar.adapter =
                KomentarAdapter(komentar) {
                }
            tv_komentar.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)

        }


    }

}