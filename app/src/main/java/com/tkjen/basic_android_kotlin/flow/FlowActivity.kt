package com.tkjen.basic_android_kotlin.flow

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tkjen.basic_android_kotlin.R
import com.tkjen.basic_android_kotlin.databinding.ActivityFlowBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowActivity : ComponentActivity(R.layout.activity_flow) {

    private lateinit var binding : ActivityFlowBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: FlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


        adapter = UserAdapter(emptyList()) { user ->
            viewModel.onUserClick(user)
        }
        binding.userRecyclerView.adapter = adapter
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)


        // StateFlow: danh sách người dùng
        lifecycleScope.launch {
            viewModel.usersState.collect {
                adapter.submitList(it)
            }
        }
        // StateFlow: loading
        lifecycleScope.launch {
            viewModel.loading.collect {
                binding.progressBar.visibility = if (it) ProgressBar.VISIBLE else ProgressBar.GONE
            }
        }
        // SharedFlow: Toast sự kiện
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest {
                Toast.makeText(this@FlowActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.loadUsers()

    }
}