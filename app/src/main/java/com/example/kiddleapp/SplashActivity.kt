package com.example.kiddleapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.Onboarding.OnBoardingActivity


class SplashActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)
        val jenis_akses = sharedPreferences.getBoolean("pernah_login", false)

        if(jenis_akses) {
            if(sharedPreferences.getString("id_guru", "").isNullOrEmpty()) {
                Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 1000)
            } else {
                Handler().postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 1000)
            }
        } else {
            Handler().postDelayed({
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }, 1000)
        }

        supportActionBar?.hide()
    }
}