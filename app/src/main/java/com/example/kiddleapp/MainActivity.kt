package com.example.kiddleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kiddleapp.fragment.Fragment_Beranda
import com.example.kiddleapp.fragment.Fragment_Murid
import com.example.kiddleapp.fragment.Fragment_Pengaturan
import com.example.kiddleapp.fragment.Fragment_Sekolah
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //untuk assign fragment
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.menu_beranda -> {
                val fragment = Fragment_Beranda()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_pengaturan -> {
                val fragment = Fragment_Pengaturan()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_sekolah -> {
                val fragment = Fragment_Sekolah()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_murid -> {
                val fragment = Fragment_Murid()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    //untuk mengisi frame layout dengan fragment
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //menambahkan listener
        bottom_navigation.setOnNavigationItemSelectedListener(navListener)

        //fragment home otomatis terbuka pertama kali
        val fragment = Fragment_Beranda()
        addFragment(fragment)

    }
}