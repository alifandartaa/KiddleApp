package com.example.kiddleapp.Presensi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Presensi.adapter.ListPresensiAdapter
import com.example.kiddleapp.Presensi.model.PresensiMurid
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_detail_presensi.*
import kotlinx.android.synthetic.main.item_detail_presensi.*

class DetailPresensiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_presensi)

        supportActionBar?.hide()

        val list = ArrayList<PresensiMurid>()
        val test = PresensiMurid("Nur Fajri Hayyuni Maulidya", "Hadir")
        val test2 = PresensiMurid("Nur Fajri Hayyuni Maulidya", "Alpha")
        val test3 = PresensiMurid("Nur Fajri Hayyuni Maulidya", "Hadir")
        val test4 = PresensiMurid("Nur Fajri Hayyuni Maulidya", "Alpha")
        val test5 = PresensiMurid("Nur Fajri Hayyuni Maulidya", "Hadir")
        val test6 = PresensiMurid("Nur Fajri Hayyuni Maulidya", "Alpha")
        list.add(test)
        list.add(test2)
        list.add(test3)
        list.add(test4)
        list.add(test5)
        list.add(test6)
        rv_presensi_murid.layoutManager = LinearLayoutManager(this)
        val adapter = ListPresensiAdapter()
        adapter.notifyDataSetChanged()
        adapter.addItemToList(list)
        rv_presensi_murid.adapter = adapter

        adapter.setOnItemClickCallback(object : ListPresensiAdapter.onClickCallback {
            override fun onItemClick(data: PresensiMurid) {
                expend_layout_presensi.isExpanded = true
            }
        })

        ic_back_detailpres.setOnClickListener {
            startActivity(Intent(this@DetailPresensiActivity, PresensiActivity::class.java))
        }
                expend_layout_presensi.expand()
            }
        })
    }
}