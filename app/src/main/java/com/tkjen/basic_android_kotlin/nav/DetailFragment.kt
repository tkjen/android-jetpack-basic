package com.tkjen.basic_android_kotlin.nav

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tkjen.basic_android_kotlin.R
import com.tkjen.basic_android_kotlin.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sử dụng ViewBinding
        binding = FragmentDetailBinding.bind(view)

        // Nhận dữ liệu từ Bundle (cách 2 - không dùng Safe Args)
       // val itemId = arguments?.getString("itemIdFromHome") ?: "No ID"

        // Nhận dữ liệu từ Safe Args
        val itemId = args.itemIdFromHome
        binding.textViewDetail.text = "Received item ID: $itemId"


        Log.d("DetailFragment", "Received item ID: $itemId")


    }
}
