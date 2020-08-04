package com.example.kiddleapp.Kegiatan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Kegiatan.Adapter.Adapter_Kegiatan

import com.example.kiddleapp.Kegiatan.Model.Kegiatan
import com.example.kiddleapp.R

import kotlinx.android.synthetic.main.activity_kegiatan.*

class KegiatanActivity : AppCompatActivity() {

    //untuk menyimpan TugasActivity
    private var model = ArrayList<Kegiatan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kegiatan)
        if (intent.getStringExtra("jenis") == "KEGIATAN") {

            //recyclerView murid
            rv_jenis_fungsi.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            tv_jenis_fungsi.setText(R.string.kegiatan)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_kegiatan)
            //mengkosongkan isi arraylist
            model.clear()

            //bisa diganti dengan data dari firebase
            val temp = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                0,
                R.raw.video_tugas,
                "https://youtu.be/g9aXIpJFKyU"
            )
            model.add(temp)

            val temp2 = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                R.drawable.banner_kegiatan,
                0,
                ""
            )
            model.add(temp2)

            val temp3 = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                R.drawable.banner_kegiatan,
                0,
                "https://youtu.be/g9aXIpJFKyU"
            )
            model.add(temp3)


            //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi


        } else if (intent.getStringExtra("jenis") == "PARENTING") {
            //recyclerView murid
            rv_jenis_fungsi.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            tv_jenis_fungsi.setText(R.string.parenting)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_parenting)

            //mengkosongkan isi arraylist
            model.clear()

            //bisa diganti dengan data dari firebase
            val temp = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                0,
                R.raw.video_tugas,
                ""
            )
            model.add(temp)

            val temp2 = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                R.drawable.banner_parenting,
                0,
                "https://youtu.be/g9aXIpJFKyU"
            )
            model.add(temp2)

            val temp3 = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                R.drawable.banner_parenting,
                0,
                ""
            )
            model.add(temp3)


            //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi


        } else if (intent.getStringExtra("jenis") == "MATERI") {
            //recyclerView murid
            rv_jenis_fungsi.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            tv_jenis_fungsi.setText(R.string.materi)
            gambar_jenis_fungsi.setImageResource(R.drawable.ilustrasi_materi)

            //mengkosongkan isi arraylist
            model.clear()

            //bisa diganti dengan data dari firebase
            val temp = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                0,
                R.raw.video_tugas,
                ""
            )
            model.add(temp)

            val temp2 = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                R.drawable.banner_materi2,
                0,
                "https://youtu.be/g9aXIpJFKyU"
            )
            model.add(temp2)

            val temp3 = Kegiatan(
                "Hari Ibu",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
                "20 Juli 2020",
                R.drawable.banner_materi2,
                0,
                "https://youtu.be/g9aXIpJFKyU"
            )
            model.add(temp3)
            //agar murid dapat di-click sekaligus mengisi adapter dengan data di arraylist tadi

        }

        rv_jenis_fungsi.adapter =
            Adapter_Kegiatan(model) {
                val intent: Intent =
                    Intent(
                        this@KegiatanActivity,
                        DetailKegiatanActivity::class.java
                    ).putExtra("data", it)
                startActivity(intent)

            }
        btn_tambah_jenis_fungsi.setOnClickListener {
            if (intent.getStringExtra("jenis") == "KEGIATAN") {
                val intent: Intent = Intent(
                    this@KegiatanActivity,
                    EditKegiatanActivity::class.java
                ).putExtra("jenis", "TAMBAH_KEGIATAN")
                startActivity(intent)
            } else if (intent.getStringExtra("jenis") == "PARENTING") {
                val intent: Intent = Intent(
                    this@KegiatanActivity,
                    EditKegiatanActivity::class.java
                ).putExtra("jenis", "TAMBAH_PARENTING")
                startActivity(intent)
            } else if (intent.getStringExtra("jenis") == "MATERI") {
                val intent: Intent = Intent(
                    this@KegiatanActivity,
                    EditKegiatanActivity::class.java
                ).putExtra("jenis", "TAMBAH_MATERI")
                startActivity(intent)
            }

        }
        //intent untuk kembali ke halaman sebelumnya
        img_back_kegiatan.setOnClickListener {
            onBackPressed()
        }


    }


}