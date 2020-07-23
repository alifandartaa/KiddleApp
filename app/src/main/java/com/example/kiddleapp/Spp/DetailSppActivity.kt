package com.example.kiddleapp.Spp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.adapter.DetailSppAdapter
import com.example.kiddleapp.Spp.model.ItemBayarSpp
import kotlinx.android.synthetic.main.activity_detail_spp.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailSppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_spp)

        val list = ArrayList<ItemBayarSpp>()

        val test = ItemBayarSpp(
            "Nur Fajri Hayyuni Maulidya",
            "02 Maret 2020",
            "Belum dikonfirmasi",
            R.drawable.female_avatar
        )
        val test2 = ItemBayarSpp(
            "Nur Fajri Hayyuni Maulidya",
            "02 Maret 2020",
            "Belum dikonfirmasi",
            R.drawable.female_avatar
        )
        list.add(test)
        list.add(test2)

        val items = listOf("Bintang Kecil", "Bintang Besar", "Bulan Kecil", "Bulan Besar")
        val arrayDropdown = ArrayAdapter(this, R.layout.dropdown_text, items)
        (dropdown_kelas_detailspp.editText as? AutoCompleteTextView)?.setAdapter(arrayDropdown)

        tv_bulan_cardview.text = intent.getStringExtra("bulan_spp")
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        tv_value_jumlahpembayaran_detail.text =
            formatRupiah.format(intent.getIntExtra("jumlah_pembayaran", 0))

        rv_detail_spp.layoutManager = LinearLayoutManager(this)
        val adapter = DetailSppAdapter()
        adapter.notifyDataSetChanged()
        adapter.addItemToList(list)
        rv_detail_spp.adapter = adapter

        adapter.setOnItemClickCallback(object : DetailSppAdapter.onClickCallback {
            override fun onItemClick(data: ItemBayarSpp) {
                val intent = Intent(this@DetailSppActivity, KonfirmSppActivity::class.java)
                intent.putExtra("nama_murid", data.namaMurid)
                intent.putExtra("bulan_spp", data.tanggalBayar)
                intent.putExtra("konfimasi_spp", data.konfirmasi)
                intent.putExtra(
                    "jumlah_pembayaran",
                    tv_value_jumlahpembayaran_detail.text.toString()
                )
                startActivity(intent)
            }
        })

        menu_ubah_spp.setOnClickListener {
            val popup: PopupMenu = PopupMenu(this, it)
            popup.menuInflater.inflate(R.menu.menu_edit_only, popup.menu)
            popup.setOnMenuItemClickListener {
                val intent = Intent(this@DetailSppActivity, UbahSppActivity::class.java)
                //assign value ke editView nanti. kurang gambar
                intent.putExtra("jenis", "EDIT_SPP")
                intent.putExtra("judul_bulan", tv_bulan_cardview.text.toString())
                intent.putExtra(
                    "jumlah_pembayaran",
                    tv_jumlahpembayaran_detail.text
                )
                //intent tanggal belum
                startActivity(intent)
                return@setOnMenuItemClickListener true
            }
            popup.show()
        }

        img_back_detail_spp.setOnClickListener {
            finish()
        }
    }
}