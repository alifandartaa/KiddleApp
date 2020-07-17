package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Kegiatan
import com.example.kiddleapp.adapter.Adapter_Materi
import com.example.kiddleapp.adapter.Adapter_Parenting
import com.example.kiddleapp.model.Model_Kegiatan
import com.example.kiddleapp.model.Model_Materi
import com.example.kiddleapp.model.Model_Parenting
import kotlinx.android.synthetic.main.activity_kegiatan.*
import kotlinx.android.synthetic.main.activity_materi.*
import kotlinx.android.synthetic.main.activity_parenting.*
import kotlinx.android.synthetic.main.list_kegiatan.*

class Parenting : AppCompatActivity() {

    //untuk menyimpan Tugas
    private var parenting = ArrayList<Model_Parenting>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parenting)


        //recyclerView murid
        rv_parenting.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //mengkosongkan isi arraylist
        parenting.clear()

        //bisa diganti dengan data dari firebase
        val temp = Model_Parenting( "Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas)
        parenting.add(temp)

        val temp2 = Model_Parenting("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_parenting,0)
        parenting.add(temp2)

        val temp3= Model_Parenting("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_parenting,0)
        parenting.add(temp3)


        //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi
            rv_parenting.adapter = Adapter_Parenting(parenting){
              //      val intent: Intent = Intent(this@Kegiatan, Detail_Kegiatan::class.java).putExtra("data", it)
              //      startActivity(intent)

            }





        //intent untuk kembali ke halaman sebelumnya
        img_back_parenting.setOnClickListener {
            onBackPressed()
        }

        btn_tambah_parenting.setOnClickListener {
            val intent: Intent = Intent(this@Parenting, Edit_Parenting::class.java).putExtra("jenis", "TAMBAH_PARENTING")
            startActivity(intent)
        }

    }
}