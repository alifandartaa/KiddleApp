package com.example.kiddleapp.Spp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        supportActionBar!!.hide()
        var fr = supportFragmentManager.beginTransaction()
        fr.add(R.id.fragment_container, SppFragment()).commit()
    }
}