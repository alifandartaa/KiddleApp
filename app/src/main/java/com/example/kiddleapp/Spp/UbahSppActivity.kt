package com.example.kiddleapp.Spp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_ubah_spp.*
import java.util.*

class UbahSppActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_spp)

        if (intent.getStringExtra("jenis") == "EDIT_SPP") {
            et_judulpembayaran.setText(intent.getStringExtra("judul_bulan"))
            et_jumlahpembayaran.setText(intent.getIntExtra("jumlah_pembayaran", 0).toString())
            //intent tanggal belum
        }

        img_back_ubahspp.setOnClickListener {
            finish()
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btn_datepicker_spp.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    tv_value_tenggatwaktu.text = "$day $month $year"
                },
                year,
                month,
                day
            )
            dpd.show()
        }
    }
}