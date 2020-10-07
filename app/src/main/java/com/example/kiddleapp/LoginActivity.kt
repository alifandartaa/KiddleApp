package com.example.kiddleapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("KIDDLE", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("pernah_login", true).apply()

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        btn_login_admin.setOnClickListener {
            btn_login_admin.isEnabled = false
            btn_login_admin.text = "Loading"

            if(et_nip_admin.text.toString().isEmpty() || et_password_admin.text.toString().isEmpty()) {
                Toast.makeText(this, "ID Guru dan Kata Sandi harap diisi!", Toast.LENGTH_SHORT).show()
                btn_login_admin.isEnabled = true
                btn_login_admin.text = "Masuk"
            } else {
                db.document("Guru/" + et_nip_admin.text.toString()).get().addOnSuccessListener {
                    if(it.exists()) {
                        if(et_password_admin.text.toString() == it.getString("password")) {
                            sharedPreferences.edit().putString("id_guru", it.id).apply()
                            sharedPreferences.edit().putString("nama", it.getString("nama")).apply()
                            sharedPreferences.edit().putString("kelas", it.getString("jabatan")).apply()
                            sharedPreferences.edit().putString("avatar", it.getString("avatar")).apply()
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        } else {
                            Toast.makeText(this, "ID Guru atau Kata Sandi salah!", Toast.LENGTH_SHORT).show()
                            btn_login_admin.isEnabled = true
                            btn_login_admin.text = "Masuk"
                        }
                    } else {
                        Toast.makeText(this, "IDGuru atau Kata Sandi salah!", Toast.LENGTH_SHORT).show()
                        btn_login_admin.isEnabled = true
                        btn_login_admin.text = "Masuk"
                    }
                }
            }
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