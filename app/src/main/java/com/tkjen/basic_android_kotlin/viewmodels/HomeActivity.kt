package com.tkjen.basic_android_kotlin.viewmodels

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tkjen.basic_android_kotlin.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

class HomeActivity:AppCompatActivity() {

  private lateinit var binding: ActivityHomeBinding
  private val viewModel : MyViewModel by viewModels()
    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

       viewModel.counter.observe(this){ count ->
              binding.textViewCounter.text = "Counter: $count"
                Log.d(TAG, "Counter updated: $count")
         }

        viewModel.isLoading.observe(this){ isLoading ->
            when (isLoading) {
                true -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonFetch.isEnabled = false
                }
                false -> {
                    binding.progressBarLoading.visibility = View.GONE
                    binding.buttonFetch.isEnabled = true
                }
            }

        }
        binding.buttonIncrement.setOnClickListener {
            viewModel.incrementCounter()
        }
        binding.buttonFetch.setOnClickListener {
            viewModel.fetchDataFromServer()
       }



        }


    }


