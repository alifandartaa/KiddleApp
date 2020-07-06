package com.example.kiddleapp
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class Beranda : AppCompatActivity() {
    var sampleImages = intArrayOf(R.drawable.slide_1, R.drawable.slide_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        val carouselView = findViewById(R.id.carouselView) as CarouselView;
        carouselView.setPageCount(sampleImages.size);
        carouselView.setImageListener(imageListener);
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
    }
}

