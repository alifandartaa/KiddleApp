package com.example.kiddleapp.Pengumuman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Pengumuman.Adapter.PengumumanAdapter
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_pengumuman.*

class PengumumanActivity : AppCompatActivity() {

    //untuk menyimpan TugasActivity
    private var pengumuman = ArrayList<Pengumuman>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman)


        //recyclerView murid
        rv_pengumuman.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        pengumuman.clear()

        //bisa diganti dengan data dari firebase
        val temp = Pengumuman(
            "Pembelajaran Online",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
            "20 Juli 2020",
            0,
            R.raw.video_tugas
        )
        pengumuman.add(temp)

        val temp2 = Pengumuman(
            "Pembelajaran Online",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
            "20 Juli 2020",
            R.drawable.banner_pengumuman2,
            0
        )
        pengumuman.add(temp2)

        val temp3 = Pengumuman(
            "Pembelajaran Online",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
            "20 Juli 2020",
            R.drawable.banner_pengumuman2,
            0
        )
        pengumuman.add(temp3)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
        rv_pengumuman.adapter =
            PengumumanAdapter(
                pengumuman
            ) {
                val intent: Intent =
                    Intent(
                        this@PengumumanActivity,
                        DetailPengumumanActivity::class.java
                    ).putExtra("data", it)
                startActivity(intent)

            }


        //intent untuk kembali ke halaman sebelumnya
        img_back_pengumuman.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_pengumuman.setOnClickListener {
            val intent: Intent = Intent(
                this@PengumumanActivity,
                EditPengumumanActivity::class.java
            ).putExtra("jenis", "TAMBAH_PENGUMUMAN")
            startActivity(intent)
        }

    }
}