package com.example.kiddleapp.Spp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.activity_detail_spp.*

class DetailSppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_spp)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        tv_test_to_konfirm.setOnClickListener {
            val intent = Intent(this@DetailSppActivity, KonfirmSppActivity::class.java)
            startActivity(intent)
        }
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