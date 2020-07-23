package com.example.kiddleapp.Onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.LoginActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val titleFragment =
            arrayOf<String>(
                "Presensi Murid",
                "JurnalActivity KegiatanActivity",
                "Pembayaran SPP",
                "RaporActivity Murid"
            )

        val onBoardingAdapter =
            OnBoardingAdapter(
                this,
                titleFragment.size
            )
        onboarding_viewpager.adapter = onBoardingAdapter
        worm_dots_indicator.setViewPager2(onboarding_viewpager)

        btn_selesai_onboarding.setOnClickListener {
            val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}