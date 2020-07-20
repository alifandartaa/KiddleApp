package com.example.kiddleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_bantuan_login.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.message_dialog_bantuan_login))
                .setTitle(getString(R.string.title_dialog_bantuan_login))
                .setPositiveButton(getString(R.string.positive_button_dialog)) { dialog, which ->

                }
                .show()
        }
    }
}