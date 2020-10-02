package com.example.kiddleapp.Beranda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.kiddleapp.Guru.Model.Guru
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
import com.google.firebase.firestore.FirebaseFirestore
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_edit_pengumuman.*
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*

class BerandaFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var data:Pengumuman

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
                    Intent(activity, KegiatanActivity::class.java).putExtra("jenis", "MATERI")
                )
            }
            else if(position==0){
                startActivity(
                    Intent(activity, KegiatanActivity::class.java).putExtra("jenis", "PARENTING")
                )
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        //instansiasi carousel
        val carouselView = view.carouselView
        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)
        carouselView.setImageClickListener(clickListener)
        sharedPreferences = activity?.getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)!!
        db.document("Guru/${sharedPreferences.getString("id_guru", "")}").get().addOnSuccessListener {

           view.nama_guru.text = it.getString("nama")
            Glide.with(this).load(it.getString("avatar")).centerCrop().into(view.img_avatar)
//                avatar = it.getString("avatar").toString()
//                sharedPreferences.edit().putString("avatar", avatar).apply()
        }

//        view.nama_guru.text = "Bapak/Ibu " + sharedPreferences.getString("nama", "")
//
//        Glide.with(this).load(sharedPreferences.getString("avatar", "")).centerCrop().into(view.img_avatar)

        db.collection("Pengumuman").limit(1).addSnapshotListener { value, error ->
            if(error != null) return@addSnapshotListener
            for(document in value!!) {
                if(document.getString("gambar") != ""){
                    Glide.with(this).load(document.getString("gambar"))
                        .transform(RoundedCorners(32)).centerCrop().into(view.img_pengumuman_beranda)
                }else if(document.getString("video")!=""){
                    Glide.with(this).load(document.getString("video"))
                        .transform(RoundedCorners(32)).centerCrop().into(view.img_pengumuman_beranda)
                }else{
                    Glide.with(this).load(R.drawable.black_layer)
                        .transform(RoundedCorners(32)).centerCrop().into(view.img_pengumuman_beranda)
                }


                view.tv_judul_pengumuman_beranda.text = document.getString("judul")
                view.tv_tanggal_pengumuman_beranda.text = document.getString("tanggal")
                view.tv_isi_pengumuman_beranda.text = document.getString("isi")

                data = Pengumuman(
                    document.id,
                    document.getString("judul"),
                    document.getString("isi"),
                    document.getString("tanggal"),
                    document.getString("gambar"),
                    document.getString("video"))
            }
        }

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

        view.img_pengumuman_beranda.setOnClickListener {
            val intent: Intent = Intent(activity, DetailPengumumanActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }

        return view
    }

}