package com.example.kiddleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login_admin.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        tv_bantuan_login_admin.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.message_dialog_bantuan_login))
                .setTitle(getString(R.string.title_dialog_bantuan_login))
                .setPositiveButton(getString(R.string.positive_button_dialog)) { dialog, which ->

                }
                .show()
        }
    }
}