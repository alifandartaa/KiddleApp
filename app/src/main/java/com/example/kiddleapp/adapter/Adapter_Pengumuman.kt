package com.example.kiddleapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.kiddleapp.Detail_Kegiatan
import com.example.kiddleapp.Detail_Pengumuman
import com.example.kiddleapp.R
import com.example.kiddleapp.model.Model_Kegiatan
import com.example.kiddleapp.model.Model_Pengumuman


class Adapter_Pengumuman(private var data:List<Model_Pengumuman>, private val listener: (Model_Pengumuman) -> Unit):RecyclerView.Adapter<Adapter_Pengumuman.ViewHolder>()  {

    lateinit var contextAdapter:Context

    //assign value dari model ke xml
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val tv_judul:TextView = view.findViewById(R.id.tv_judul_list_pengumuman)
        private val tv_isi:TextView = view.findViewById(R.id.tv_isi_list_pengumuman)
        val img_pengumuman :ImageView = view.findViewById(R.id.img_banner_list_pengumuman)
         val vv_pengumuman :VideoView = view.findViewById(R.id.vv_banner_list_pengumuman)

        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(data: Model_Pengumuman, listener: (Model_Pengumuman) -> Unit, context: Context, position: Int) {
            tv_judul.text = data.judul
            tv_isi.text=data.isi

            if(data.gambar!=0)
            {
               img_pengumuman.setVisibility(View.VISIBLE);
                vv_pengumuman.setVisibility(View.GONE);
                img_pengumuman.setImageResource(data.gambar)
            }
            else if(data.video!=0)
            {
                img_pengumuman.setVisibility(View.VISIBLE);
                vv_pengumuman.setVisibility(View.GONE);
                vv_pengumuman.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() +"/"+data.video))
                vv_pengumuman.seekTo( 10 );
                //vv_kegiatan.start()
               // var media_Controller: MediaController = MediaController(this)
                //vv_kegiatan.setMediaController(media_Controller)
               // media_Controller.setAnchorView(vv_kegiatan)

            }

            itemView.setOnClickListener {
                listener(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.list_pengumuman, parent, false)
        return ViewHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
        // biar gambarnya ada radiusnya
        if(data[position].gambar!=0){
            val url: Int = data[position].gambar
            Glide.with(contextAdapter)
                .load(url)
                .transform(RoundedCorners(32))
                .into(holder.img_pengumuman)

        }else if(data[position].video!=0){
            val url2: Int = data[position].video
            Glide.with(contextAdapter)
                .load(Uri.parse("android.resource://" + contextAdapter.getPackageName() +"/"+url2))
                .transform(RoundedCorners(32))
                .into(holder.img_pengumuman)
        }



    }
}