package com.example.kiddleapp.Tugas.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.DetailTugasActivity
import com.example.kiddleapp.Tugas.Model.Tugas

class TugasAdapter(private var data: List<Tugas>, private val listener: (Tugas) -> Unit) :
    RecyclerView.Adapter<TugasAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listTugas = ArrayList<Tugas>()
    private val context: Context? = null

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val img_jenis: ImageView = view.findViewById(R.id.img_jenis_list_tugas)
        private val tv_judul: TextView = view.findViewById(R.id.tv_judul_list_tugas)
        private val tv_tanggal: TextView = view.findViewById(R.id.tv_tanggal_list_tugas)

        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model
        fun bindItem(data: Tugas, listener: (Tugas) -> Unit, context: Context, position: Int) {
            if (data.judul == "Motorik") {
                img_jenis.setImageResource(R.drawable.ic_motorik)
            } else if (data.judul == "Keterampilan") {
                img_jenis.setImageResource(R.drawable.ic_keterampilan)
            } else if (data.judul == "Agama") {
                img_jenis.setImageResource(R.drawable.ic_agama)
            } else if (data.judul == "Berbahasa") {
                img_jenis.setImageResource(R.drawable.ic_berbahasa)
            } else if (data.judul == "Kognitif") {
                img_jenis.setImageResource(R.drawable.ic_kognitif)
            }

            tv_judul.text = data.judul
            tv_tanggal.text=data.tanggal


            itemView.setOnClickListener {
                listener(data)

            }

            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_tugas, parent, false)
        return ViewHolder(
            inflatedView
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)

        holder.itemView.setOnClickListener(View.OnClickListener { v ->
            val intent: Intent = Intent(v.context, DetailTugasActivity::class.java).putExtra("data", data[position])
            v.context.startActivity(intent)
            (v.context as Activity).finish()
        })
    }

    fun addItemToList(list: ArrayList<Tugas>) {
        listTugas.clear()
        listTugas.addAll(list)
    }
}