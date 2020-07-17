package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.adapter.Adapter_Kegiatan

import com.example.kiddleapp.model.Model_Kegiatan

import kotlinx.android.synthetic.main.activity_edit_kegiatan.*
import kotlinx.android.synthetic.main.activity_kegiatan.*

import kotlinx.android.synthetic.main.list_kegiatan.*

class Kegiatan : AppCompatActivity() {

    //untuk menyimpan Tugas
    private var model = ArrayList<Model_Kegiatan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kegiatan)
        if(intent.getStringExtra("jenis") == "KEGIATAN") {

            //recyclerView murid
            rv_jenis_fungsi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            tv_jenis_fungsi.setText(R.string.kegiatan)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_kegiatan)
            //mengkosongkan isi arraylist
            model.clear()

            //bisa diganti dengan data dari firebase
            val temp = Model_Kegiatan( "Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas,"https://youtu.be/g9aXIpJFKyU")
            model.add(temp)

            val temp2 = Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_kegiatan,0,"")
            model.add(temp2)

            val temp3= Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_kegiatan,0,"https://youtu.be/g9aXIpJFKyU")
            model.add(temp3)


            //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi


        }else if(intent.getStringExtra("jenis") == "PARENTING"){
            //recyclerView murid
            rv_jenis_fungsi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            tv_jenis_fungsi.setText(R.string.parenting)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_parenting)

            //mengkosongkan isi arraylist
            model.clear()

            //bisa diganti dengan data dari firebase
            val temp = Model_Kegiatan( "Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas,"")
            model.add(temp)

            val temp2 = Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_parenting,0,"https://youtu.be/g9aXIpJFKyU")
            model.add(temp2)

            val temp3= Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_parenting,0,"")
            model.add(temp3)


            //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi



        }else if(intent.getStringExtra("jenis") == "MATERI"){
            //recyclerView murid
            rv_jenis_fungsi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            tv_jenis_fungsi.setText(R.string.materi)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_materi)

            //mengkosongkan isi arraylist
            model.clear()

            //bisa diganti dengan data dari firebase
            val temp = Model_Kegiatan( "Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020", 0,R.raw.video_tugas,"")
            model.add(temp)

            val temp2 = Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_materi2,0,"https://youtu.be/g9aXIpJFKyU")
            model.add(temp2)

            val temp3= Model_Kegiatan("Hari Ibu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget", "20 Juli 2020",R.drawable.banner_materi2,0,"https://youtu.be/g9aXIpJFKyU")
            model.add(temp3)
            //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi

        }

    rv_jenis_fungsi.adapter = Adapter_Kegiatan(model){
        val intent: Intent = Intent(this@Kegiatan, Detail_Kegiatan::class.java).putExtra("data", it)
        startActivity(intent)

    }
    btn_tambah_jenis_fungsi.setOnClickListener {
        val intent: Intent = Intent(this@Kegiatan, Edit_Kegiatan::class.java).putExtra("jenis", "TAMBAH")
        startActivity(intent)
    }
        //intent untuk kembali ke halaman sebelumnya
        img_back_kegiatan.setOnClickListener {
            onBackPressed()
        }


    }



}