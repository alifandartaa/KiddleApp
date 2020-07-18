package com.example.kiddleapp.Presensi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.Presensi.model.PresensiMurid
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.item_detail_presensi.view.*

class ListPresensiAdapter : RecyclerView.Adapter<ListPresensiAdapter.ListViewHolder>() {
    var listItem = ArrayList<PresensiMurid>()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(presensiMurid: PresensiMurid) {
            with(itemView) {
                tv_nama_murid_presensi.text = presensiMurid.namaMurid
                when (presensiMurid.kehadiran) {
                    "Hadir" ->
                        option_hadir.isChecked = true
                    "Sakit" ->
                        option_hadir.isChecked = true
                    "Izin" ->
                        option_hadir.isChecked = true
                    "Alpha" ->
                        option_hadir.isChecked = true
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_presensi, parent, false)
        return ListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listItem[position])
    }
}