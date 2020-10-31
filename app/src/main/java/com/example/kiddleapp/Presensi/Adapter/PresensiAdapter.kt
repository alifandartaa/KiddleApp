package com.example.kiddleapp.Presensi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddleapp.Presensi.Model.PresensiMurid
import com.example.kiddleapp.R
import com.google.firebase.firestore.FirebaseFirestore



class PresensiAdapter(private var data: List<PresensiMurid>, private val listener: (PresensiMurid) -> Unit) :
    RecyclerView.Adapter<PresensiAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context
    private var listMurid = ArrayList<PresensiMurid>()

    //assign value dari model ke xml
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_nama: TextView = view.findViewById(R.id.tv_nama_murid_presensi)
        val opt_hadir: RadioButton = view.findViewById(R.id.option_hadir)
        val opt_izin: RadioButton = view.findViewById(R.id.option_izin)
        val opt_sakit: RadioButton = view.findViewById(R.id.option_sakit)
        val opt_alpha: RadioButton = view.findViewById(R.id.option_alpha)

        fun bindItem(data: PresensiMurid, listener: (PresensiMurid) -> Unit, context: Context, position: Int) {
            FirebaseFirestore.getInstance().collection("Murid").whereEqualTo("nomor",data.id_murid).addSnapshotListener { result, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (document in result!!) {
                    tv_nama.text = document.getString("nama")
                }
            }

            if(data.kehadiran=="hadir" || data.kehadiran == "libur"){
                opt_hadir.isChecked = true
            }else if(data.kehadiran=="sakit"){
                opt_sakit.isChecked = true
            }else if(data.kehadiran=="alpha"){
                opt_alpha.isChecked = true
            }else if(data.kehadiran=="izin"){
                opt_izin.isChecked = true
            }

            itemView.setOnClickListener {
                listener(data)

            }
            listener.invoke(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.item_detail_presensi, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)

        holder.opt_hadir.setOnClickListener{
            data[position].kehadiran = "hadir"
        }
        holder.opt_sakit.setOnClickListener{
            data[position].kehadiran = "sakit"
        }
        holder.opt_izin.setOnClickListener{
            data[position].kehadiran = "izin"
        }
        holder.opt_alpha.setOnClickListener{
            data[position].kehadiran = "alpha"
        }
    }

    fun addItemToList(list: ArrayList<PresensiMurid>) {
        listMurid.clear()
        listMurid.addAll(list)
    }


}
