package com.example.kiddleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kiddleapp.Beranda.BerandaFragment
import com.example.kiddleapp.Murid.MuridFragment
import com.example.kiddleapp.Pengaturan.PengaturanFragment
import com.example.kiddleapp.Sekolah.SekolahFragment
import com.example.kiddleapp.Spp.SppFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //untuk assign fragment
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.menu_beranda -> {
                val fragment = BerandaFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_pengaturan -> {
                val fragment = PengaturanFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_sekolah -> {
                val fragment = SekolahFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_murid -> {
                val fragment = MuridFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_pembayaran -> {
                val fragment = SppFragment()
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

        if (intent.getStringExtra("jenis") == "murid") {
            val fragment = MuridFragment()
            addFragment(fragment)

        }else{
            //fragment home otomatis terbuka pertama kali
            val fragment = BerandaFragment()
            addFragment(fragment)
        }


    }
}