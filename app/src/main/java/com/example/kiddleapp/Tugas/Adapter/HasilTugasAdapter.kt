package com.example.kiddleapp.Tugas.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiddleapp.R
import com.example.kiddleapp.Tugas.DetailTugasActivity
import com.example.kiddleapp.Tugas.Model.HasilTugas
import com.example.kiddleapp.Tugas.Model.Tugas
import kotlinx.android.synthetic.main.activity_detail_tugas.*

class HasilTugasAdapter(
    private var data: List<HasilTugas>,
    private val listener: (HasilTugas) -> Unit
) : RecyclerView.Adapter<HasilTugasAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listHasil = ArrayList<HasilTugas>()

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img_avatar: ImageView = view.findViewById(R.id.img_avatar_murid_tugas)
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_murid_tugas)
        private val tv_tanggal: TextView = view.findViewById(R.id.tv_tanggal_murid_tugas)
        private val img_download_tugas:ImageView=view.findViewById(R.id.img_download_tugas)
        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(
            data: HasilTugas,
            listener: (HasilTugas) -> Unit,
            context: Context,
            position: Int
        ) {

            Glide.with(context).load(data.avatar).centerCrop().into(img_avatar)
            tv_nama.text = data.nama
            tv_tanggal.text = data.tanggal



            itemView.setOnClickListener {
                listener(data)
            }

            listener.invoke(data)
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

        holder.itemView.setOnClickListener(View.OnClickListener { v ->
         //   val intent: Intent = Intent(v.context, DetailTugasActivity::class.java).putExtra("data", data[position])
         //   v.context.startActivity(intent)
        })
    }

    fun addItemToList(list: ArrayList<HasilTugas>) {
        listHasil.clear()
        listHasil.addAll(list)
    }
}