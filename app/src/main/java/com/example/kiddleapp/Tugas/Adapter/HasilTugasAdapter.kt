package com.example.kiddleapp.Tugas.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.Model.HasilTugas

class HasilTugasAdapter(
    private var data: List<HasilTugas>,
    private val listener: (HasilTugas) -> Unit
) : RecyclerView.Adapter<HasilTugasAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_avatar_murid_tugas)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_murid_tugas)
        private val tv_tanggal: TextView = view.findViewById(R.id.tv_tanggal_murid_tugas)
        private val tv_jam: TextView = view.findViewById(R.id.tv_jam_murid_tugas)

        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(
            data: HasilTugas,
            listener: (HasilTugas) -> Unit,
            context: Context,
            position: Int
        ) {
            img_avatar.setImageResource(data.avatar)
            tv_nama.text = data.nama
            tv_tanggal.text = data.tanggal
            tv_jam.text = data.jam

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_hasil_tugas, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}