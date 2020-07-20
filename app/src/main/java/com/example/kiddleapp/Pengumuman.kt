package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Kegiatan
import com.example.kiddleapp.adapter.Adapter_Pengumuman
import com.example.kiddleapp.model.Model_Kegiatan
import com.example.kiddleapp.model.Model_Pengumuman
import kotlinx.android.synthetic.main.activity_kegiatan.*
import kotlinx.android.synthetic.main.activity_pengumuman.*
import kotlinx.android.synthetic.main.list_kegiatan.*

class Pengumuman : AppCompatActivity() {

    //untuk menyimpan Tugas
    private var pengumuman = ArrayList<Model_Pengumuman>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman)


        //recyclerView murid
        rv_pengumuman.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        pengumuman.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Pengumuman( "Pembelajaran Online", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas)
        pengumuman.add(temp)

        val temp2 = Model_Pengumuman("Pembelajaran Online", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_pengumuman2,0)
        pengumuman.add(temp2)

        val temp3= Model_Pengumuman("Pembelajaran Online", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_pengumuman2,0)
        pengumuman.add(temp3)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
            rv_pengumuman.adapter = Adapter_Pengumuman(pengumuman){
                val intent: Intent = Intent(this@Pengumuman, Detail_Pengumuman::class.java).putExtra("data", it)
                 startActivity(intent)

            }


        //intent untuk kembali ke halaman sebelumnya
        img_back_pengumuman.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_pengumuman.setOnClickListener {
            val intent: Intent = Intent(this@Pengumuman, Edit_Pengumuman::class.java).putExtra("jenis", "TAMBAH_PENGUMUMAN")
            startActivity(intent)
        }

    }
}