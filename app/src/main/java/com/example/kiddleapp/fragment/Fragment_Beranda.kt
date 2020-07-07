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

    var sampleImages = intArrayOf(R.drawable.slide_1, R.drawable.slide_2)

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
    }

    var clickListener: ImageClickListener = object :ImageClickListener {
        override fun onClick(position: Int) {
            Toast.makeText(activity, "Clicked $position", Toast.LENGTH_SHORT).show()
        }

    }

    var notifListener:View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            startActivity(Intent(activity, Notifikasi::class.java))
        }

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val carouselView = carouselView
        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(imageListener)
        carouselView.setImageClickListener(clickListener)
        img_notification.setOnClickListener(notifListener)

    }
}