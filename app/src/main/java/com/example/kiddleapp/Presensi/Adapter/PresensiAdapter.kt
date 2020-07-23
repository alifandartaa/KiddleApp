package com.example.kiddleapp.Presensi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.Presensi.Model.PresensiMurid
import com.example.kiddleapp.R
import kotlinx.android.synthetic.main.item_detail_presensi.view.*

class PresensiAdapter : RecyclerView.Adapter<PresensiAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: onClickCallback
    var listItem = ArrayList<PresensiMurid>()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(presensiMurid: PresensiMurid) {
            with(itemView) {
                tv_nama_murid_presensi.text = presensiMurid.namaMurid
                if (presensiMurid.kehadiran == "Hadir") {
                    option_hadir.isChecked = true
                } else if (presensiMurid.kehadiran == "Alpha") {
                    option_alpha.isChecked = true
                }
            }
        }

    }

    fun addItemToList(list: ArrayList<PresensiMurid>) {
        listItem.clear()
        listItem.addAll(list)
    }

    fun setOnItemClickCallback(onItemClickCallback: onClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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

    interface onClickCallback {
        fun onItemClick(data: PresensiMurid)
    }
}