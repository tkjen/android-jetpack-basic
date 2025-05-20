package com.tkjen.basic_android_kotlin.nav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tkjen.basic_android_kotlin.R
import com.tkjen.basic_android_kotlin.databinding.FragmentHomeBinding
import com.tkjen.basic_android_kotlin.viewmodels.MyViewModel

class HomeFragment:Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding

    private val viewModel: MyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        binding.buttonToDetail.setOnClickListener {
            val itemIdToSend = "some_item_id_123"
            val bundle = Bundle().apply {
                putString("itemIdFromHome", itemIdToSend)
            }
            val action = HomeFragmentDirections
                .actionHomeFragmentToDetailFragment(itemIdToSend)
           // findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)

        }


    }
}