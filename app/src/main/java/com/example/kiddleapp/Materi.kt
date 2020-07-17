package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Kegiatan
import com.example.kiddleapp.adapter.Adapter_Materi
import com.example.kiddleapp.model.Model_Kegiatan
import com.example.kiddleapp.model.Model_Materi
import kotlinx.android.synthetic.main.activity_kegiatan.*
import kotlinx.android.synthetic.main.activity_materi.*
import kotlinx.android.synthetic.main.list_kegiatan.*

class Materi : AppCompatActivity() {

    //untuk menyimpan Tugas
    private var materi = ArrayList<Model_Materi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)


        //recyclerView murid
        rv_materi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        materi.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Materi( "Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas)
        materi.add(temp)

        val temp2 = Model_Materi("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_materi2,0)
        materi.add(temp2)

        val temp3= Model_Materi("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_materi2,0)
        materi.add(temp3)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
            rv_materi.adapter = Adapter_Materi(materi){
              //      val intent: Intent = Intent(this@Kegiatan, Detail_Kegiatan::class.java).putExtra("data", it)
              //      startActivity(intent)

            }





        //intent untuk kembali ke halaman sebelumnya
        img_back_materi.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_materi.setOnClickListener {
            val intent: Intent = Intent(this@Materi, Edit_Materi::class.java).putExtra("jenis", "TAMBAH_Materi")
            startActivity(intent)
        }

    }
}