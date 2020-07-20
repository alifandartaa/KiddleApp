package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val titleFragment =
            arrayOf<String>("Presensi Murid", "Jurnal Kegiatan", "Pembayaran SPP", "Rapor Murid")

        val onBoardingAdapter = OnBoardingAdapter(this, titleFragment.size)
        onboarding_viewpager.adapter = onBoardingAdapter

        btn_lewati_onboarding.setOnClickListener {
            val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        btn_selesai_onboarding.setOnClickListener {
            val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
            startActivity(intent)
        }
//        val index = onboarding_viewpager.currentItem
//        if(index == 3){
//            btn_selesai_onboarding.visibility == View.VISIBLE
//        }
    }

}