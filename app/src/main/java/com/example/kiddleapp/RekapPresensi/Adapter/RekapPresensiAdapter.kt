package com.example.kiddleapp.RekapPresensi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.R
import com.example.kiddleapp.RekapPresensi.Model.RekapPresensi

class RekapPresensiAdapter(
    private var data: List<RekapPresensi>,
    private val listener: (RekapPresensi) -> Unit
) : RecyclerView.Adapter<RekapPresensiAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_rekap_bulan: TextView = view.findViewById(R.id.tv_rekap_bulan)
        private val tv_rekap_hadir: TextView = view.findViewById(R.id.tv_rekap_hadir)
        private val tv_rekap_sakit: TextView = view.findViewById(R.id.tv_rekap_sakit)
        private val tv_rekap_izin: TextView = view.findViewById(R.id.tv_rekap_izin)
        private val tv_rekap_alpha: TextView = view.findViewById(R.id.tv_rekap_alpha)

        fun bindItem(
            data: RekapPresensi,
            listener: (RekapPresensi) -> Unit,
            context: Context,
            position: Int
        ) {
            tv_rekap_bulan.text = data.bulan
            tv_rekap_hadir.text = data.hadir
            tv_rekap_izin.text = data.izin
            tv_rekap_sakit.text = data.sakit
            tv_rekap_alpha.text = data.alpha

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.list_rekap_presensi, parent, false)
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