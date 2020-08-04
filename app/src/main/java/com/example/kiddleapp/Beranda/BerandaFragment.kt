package com.example.kiddleapp.Beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.kiddleapp.Jurnal.JurnalActivity
import com.example.kiddleapp.Kegiatan.KegiatanActivity
import com.example.kiddleapp.Notifikasi.NotifikasiActivity
import com.example.kiddleapp.Pengumuman.DetailPengumumanActivity
import com.example.kiddleapp.Pengumuman.Model.Pengumuman
import com.example.kiddleapp.Pengumuman.PengumumanActivity
import com.example.kiddleapp.Presensi.PresensiActivity
import com.example.kiddleapp.Profil.ProfilActivity
import com.example.kiddleapp.R
import com.example.kiddleapp.Rapor.RaporActivity
import com.example.kiddleapp.Tugas.TugasActivity
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_beranda.view.*

class BerandaFragment : Fragment() {

    //tempat menaruh image pada carousel
    var sampleImages = intArrayOf(R.drawable.slide_1, R.drawable.slide_2)

    //assign gambar ke xml
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
    }

    //jika gambar di carousel di-click bisa diganti menjadi intent
    var clickListener: ImageClickListener = object :ImageClickListener {
        override fun onClick(position: Int) {

            if(position == 1){
                startActivity(
                    Intent(activity, KegiatanActivity::class.java).putExtra(
                        "jenis",
                        "MATERI"
                    )
                )
            }
            else if(position==0){
                startActivity(
                    Intent(activity, KegiatanActivity::class.java).putExtra(
                        "jenis",
                        "PARENTING"
                    )
                )
            }

        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        //instansiasi carousel
        val carouselView = view.carouselView
        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)
        carouselView.setImageClickListener(clickListener)

        view.keperluan_presensi.setOnClickListener {
            startActivity(Intent(activity, PresensiActivity::class.java))
        }

        //intent untuk menuju halaman notifikasi
        view.img_notification.setOnClickListener {
            startActivity(Intent(activity, NotifikasiActivity::class.java))
        }

        //intent untuk menuju halaman rapor
        view.keperluan_rapor.setOnClickListener {
            startActivity(Intent(activity, RaporActivity::class.java))
        }

        //intent untuk menuju halaman tugas
        view.keperluan_tugas.setOnClickListener {
            startActivity(Intent(activity, TugasActivity::class.java))
        }

        //intent untuk menuju halaman jurnal
        view.keperluan_jurnal.setOnClickListener {
            startActivity(Intent(activity, JurnalActivity::class.java))
        }

        //intent untuk menuju halaman pengumuman
        view.tv_lihat_pengumuman.setOnClickListener {
            startActivity(Intent(activity, PengumumanActivity::class.java))
        }

        //intent untuk menuju halaman profil
        view.img_avatar.setOnClickListener {
            startActivity(
                Intent(activity, ProfilActivity::class.java).putExtra(
                    "tipeAkses",
                    "PROFIL"
                )
            )
        }

        //pengumuman bisa diubah dari data firebase
        val temp = Pengumuman(
            "Pembelajaran Online",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vitae tellus feugiat, efficitur lacus nec, maximus felis. Maecenas ultrices tempor enim, et malesuada nisl lacinia eget",
            "20 Juli 2020",
            R.drawable.banner_pengumuman2,
            0
        )

        view.img_pengumuman_beranda.setOnClickListener {
            val intent: Intent =
                Intent(activity, DetailPengumumanActivity::class.java).putExtra("data", temp)
            startActivity(intent)
        }

        return view
    }

}