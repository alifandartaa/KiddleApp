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
import com.example.kiddleapp.Detail_Materi
import com.example.kiddleapp.R
import com.example.kiddleapp.model.Model_Kegiatan
import com.example.kiddleapp.model.Model_Materi


class Adapter_Materi(private var data:List<Model_Materi>, private val listener: (Model_Materi) -> Unit):RecyclerView.Adapter<Adapter_Materi.ViewHolder>()  {

    lateinit var contextAdapter:Context

    //assign value dari model ke xml
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val tv_judul:TextView = view.findViewById(R.id.tv_judul_list_materi)
        val img_materi :ImageView = view.findViewById(R.id.img_banner_list_materi)
         val vv_materi :VideoView = view.findViewById(R.id.vv_banner_list_materi)
         val btn :Button = view.findViewById(R.id.btn_list_materi)
        // kalau mau ditambah kelas apa atau deksripsi lain jangan lupa ubah layout dan model

        fun bindItem(data:Model_Materi, listener: (Model_Materi) -> Unit, context: Context, position: Int) {
            tv_judul.text = data.judul

            if(data.gambar!=0)
            {
               img_materi.setVisibility(View.VISIBLE);
                vv_materi.setVisibility(View.GONE);
                img_materi.setImageResource(data.gambar)
            }
            else if(data.video!=0)
            {
                img_materi.setVisibility(View.VISIBLE);
                vv_materi.setVisibility(View.GONE);
                vv_materi.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() +"/"+data.video))
                vv_materi.seekTo( 10 );
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
        val inflatedView: View = layoutInflater.inflate(R.layout.list_materi, parent, false)
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
                .into(holder.img_materi)

        }else if(data[position].video!=0){
            val url2: Int = data[position].video
            Glide.with(contextAdapter)
                .load(Uri.parse("android.resource://" + contextAdapter.getPackageName() +"/"+url2))
                .transform(RoundedCorners(32))
                .into(holder.img_materi)
        }
        //biar tobolnya bisa diklik seklian parsng data
            holder.btn.setOnClickListener { v ->
            val i = Intent(v.context, Detail_Materi::class.java).putExtra("data",data[position])
            v.context.startActivity(i)
        }


    }
}