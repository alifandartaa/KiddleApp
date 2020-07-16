package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Kegiatan
import com.example.kiddleapp.model.Model_Kegiatan
import kotlinx.android.synthetic.main.activity_kegiatan.*
import kotlinx.android.synthetic.main.list_kegiatan.*

class Kegiatan : AppCompatActivity() {

    //untuk menyimpan Tugas
    private var kegiatan = ArrayList<Model_Kegiatan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kegiatan)


        //recyclerView murid
        rv_kegiatan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        kegiatan.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Kegiatan( "Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas)
        kegiatan.add(temp)

        val temp2 = Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_kegiatan,0)
        kegiatan.add(temp2)

        val temp3= Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_kegiatan,0)
        kegiatan.add(temp3)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
            rv_kegiatan.adapter = Adapter_Kegiatan(kegiatan){
              //      val intent: Intent = Intent(this@Kegiatan, Detail_Kegiatan::class.java).putExtra("data", it)
              //      startActivity(intent)

            }





        //intent untuk kembali ke halaman sebelumnya
        img_back_kegiatan.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_kegiatan.setOnClickListener {
            val intent: Intent = Intent(this@Kegiatan, Edit_Kegiatan::class.java).putExtra("jenis", "TAMBAH_TUGAS")
            startActivity(intent)
        }

    }
}