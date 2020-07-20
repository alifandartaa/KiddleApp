package com.example.kiddleapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.model.Model_Guru
import kotlinx.android.synthetic.main.list_guru.view.*

class Adapter_Guru(private var data:List<Model_Guru>, private val listener: (Model_Guru) -> Unit):RecyclerView.Adapter<Adapter_Guru.ViewHolder>() {

    lateinit var contextAdapter:Context

    //assign value dari model ke xml
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        private val img_avatar:ImageView = view.findViewById(R.id.img_avatar_guru)
        private val tv_nama:TextView = view.findViewById(R.id.tv_nama_guru)
        private val tv_kontak:TextView = view.findViewById(R.id.tv_kontak_guru)
        private val tv_jabatan:TextView = view.findViewById(R.id.tv_jabatan_guru)

        fun bindItem(data:Model_Guru, listener: (Model_Guru) -> Unit, context: Context, position: Int) {
            img_avatar.setImageResource(data.avatar)
            tv_nama.text = data.nama
            tv_kontak.text = data.kontak
            tv_jabatan.text = data.jabatan

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView:View = layoutInflater.inflate(R.layout.list_guru, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}