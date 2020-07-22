package com.example.kiddleapp.Spp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.Spp.adapter.DetailSppAdapter
import com.example.kiddleapp.Spp.model.ItemBayarSpp
import kotlinx.android.synthetic.main.activity_detail_spp.*

class DetailSppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_spp)

        tv_test_to_konfirm.setOnClickListener {
            val intent = Intent(this@DetailSppActivity, KonfirmSppActivity::class.java)
            startActivity(intent)
        }

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
        rv_detail_spp.layoutManager = LinearLayoutManager(this)
        val adapter = DetailSppAdapter()
        adapter.notifyDataSetChanged()
        adapter.addItemToList(list)
        rv_detail_spp.adapter = adapter

        adapter.setOnItemClickCallback(object : DetailSppAdapter.onClickCallback {
            override fun onItemClick(data: ItemBayarSpp) {
                val intent = Intent(this@DetailSppActivity, KonfirmSppActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_spp, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit_spp) {
            val intent = Intent(this@DetailSppActivity, UbahSppActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}