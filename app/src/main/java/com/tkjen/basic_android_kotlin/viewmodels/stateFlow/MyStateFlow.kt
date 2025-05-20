package com.tkjen.basic_android_kotlin.viewmodels.stateFlow

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tkjen.basic_android_kotlin.databinding.ActivityMyStateflowBinding
import kotlinx.coroutines.launch

class MyStateFlow:AppCompatActivity() {
        private lateinit var binding: ActivityMyStateflowBinding
       private val viewModel: MyViewModelWithStateFlow by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMyStateflowBinding.inflate(layoutInflater)
            setContentView(binding.root)

          binding.buttonLoadStateFlow.setOnClickListener {
              viewModel.loadData()
          }

          lifecycleScope.launch {
              viewModel.uiState.collect{ state ->
                  binding.textViewStateFlowMessage.text = state.message
                  binding.progressBarStateFlow.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                  binding.buttonLoadStateFlow.isEnabled = !state.isLoading
                  Log.d("MyStateFlowFragment", "UI State updated: ${state.message}, isLoading: ${state.isLoading}")

              }
          }
        }

}