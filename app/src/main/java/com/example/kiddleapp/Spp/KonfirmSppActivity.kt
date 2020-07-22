package com.example.kiddleapp.Spp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_konfirm_spp.*

class KonfirmSppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirm_spp)

        tv_nama_murid_konfirm.text = intent.getStringExtra("nama_murid")
        tv_value_bulankonfirm.text = intent.getStringExtra("bulan_spp")
        tv_value_statuskonfirm.text = intent.getStringExtra("konfimasi_spp")
        tv_value_jumlahkonfirm.text = intent.getStringExtra("jumlah_pembayaran")

        img_back_konfirm_spp.setOnClickListener {
            finish()
        }
    }
}