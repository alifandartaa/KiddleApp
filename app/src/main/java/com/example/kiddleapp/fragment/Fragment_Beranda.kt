package com.example.kiddleapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kiddleapp.Notifikasi
import com.example.kiddleapp.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_beranda.*

class Fragment_Beranda : Fragment() {

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
            Toast.makeText(activity, "Clicked $position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val carouselView = carouselView

        //carousel
        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)
        carouselView.setImageClickListener(clickListener)

        //intent ke notifikasi
        img_notification.setOnClickListener{
            startActivity(Intent(activity, Notifikasi::class.java))
        }

    }
}