package com.example.kiddleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_on_boarding.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OnBoardingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnBoardingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var positionFragment: Int = 0
    private val titleFragment =
        arrayOf("Presensi Murid", "Jurnal Kegiatan", "Pembayaran SPP", "Rapor Murid")
    private val descriptionFragment = arrayOf(
        "Membantu wali murid mengetahui kehadiran anak setiap harinya",
        "Semua kegiatan pembelajaran dapat diakses dengan mudah",
        "Bayar SPP tepat waktu dan verifikasi pembayaran dengan satu sentuhan",
        "Hasil belajar dapat dipantau tanpa harus beranjak dari rumah"
    )
    private val imageFragment = arrayOf(
        R.drawable.onboarding5,
        R.drawable.onboarding6,
        R.drawable.onboarding7,
        R.drawable.onboarding8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            positionFragment = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title_onboarding.text = titleFragment[positionFragment]
        when (title_onboarding.text) {
            titleFragment[0] ->
                tv_description_onboarding.text = descriptionFragment[0]
            titleFragment[1] ->
                tv_description_onboarding.text = descriptionFragment[1]
            titleFragment[2] ->
                tv_description_onboarding.text = descriptionFragment[2]
            titleFragment[3] ->
                tv_description_onboarding.text = descriptionFragment[3]
        }


        when (title_onboarding.text) {
            titleFragment[0] ->
                iv_onboarding.setImageResource(imageFragment[0])
            titleFragment[1] ->
                iv_onboarding.setImageResource(imageFragment[1])
            titleFragment[2] ->
                iv_onboarding.setImageResource(imageFragment[2])
            titleFragment[3] ->
                iv_onboarding.setImageResource(imageFragment[3])
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OnBoardingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            OnBoardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}