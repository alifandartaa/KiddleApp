package com.example.kiddleapp.Spp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddleapp.R
import com.example.kiddleapp.Spp.adapter.SppAdapter
import com.example.kiddleapp.Spp.model.ItemSpp
import kotlinx.android.synthetic.main.fragment_spp.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SppFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spp, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = ArrayList<ItemSpp>()

        val test = ItemSpp("Maret 2020", 330000)
        val test2 = ItemSpp("Februari 2020", 330000)
        list.add(test)
        list.add(test2)

        rv_spp.layoutManager = LinearLayoutManager(activity)
        val adapter = SppAdapter()
        adapter.notifyDataSetChanged()
        adapter.addItemToList(list)
        rv_spp.adapter = adapter

        adapter.setOnItemClickCallback(object : SppAdapter.OnItemClickCallback {
            override fun onItemClick(data: ItemSpp) {
                val intent = Intent(activity, DetailSppActivity::class.java)
                startActivity(intent)
            }

        })
        fab_tambah_spp.setOnClickListener {
            val intent = Intent(activity, UbahSppActivity::class.java)
            startActivity(intent)
        }
        iv_test.setOnClickListener {
            val intent = Intent(activity, DetailSppActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SppFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SppFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}