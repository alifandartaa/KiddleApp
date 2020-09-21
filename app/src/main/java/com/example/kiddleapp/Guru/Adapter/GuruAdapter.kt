package com.example.kiddleapp.Guru.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiddleapp.Guru.Model.Guru
import com.example.kiddleapp.R

class GuruAdapter(private var data: List<Guru>, private val listener: (Guru) -> Unit) :
    RecyclerView.Adapter<GuruAdapter.ViewHolder>() {
    lateinit var contextAdapter: Context
    private val listGuru = ArrayList<Guru>()

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_avatar_guru)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_guru)
        private val tv_kontak: TextView = view.findViewById(R.id.tv_kontak_guru)
        private val tv_jabatan: TextView = view.findViewById(R.id.tv_jabatan_guru)

        fun bindItem(data: Guru, listener: (Guru) -> Unit, context: Context, position: Int) {
            Glide.with(context).load(data.avatar).centerCrop().into(img_avatar)
            tv_nama.text = data.nama
            tv_kontak.text = data.kontak
            tv_jabatan.text = data.jabatan

            itemView.setOnClickListener {
                listener(data)
            }

            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_guru, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + data[position].kontak)
            it.context.startActivity(intent)
        }
    }

    fun addItemToList(list: ArrayList<Guru>) {
        listGuru.clear()
        listGuru.addAll(list)
    }
}