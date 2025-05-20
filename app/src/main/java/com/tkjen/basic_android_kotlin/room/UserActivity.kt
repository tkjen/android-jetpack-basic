package com.tkjen.basic_android_kotlin.room

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tkjen.basic_android_kotlin.R
import com.tkjen.basic_android_kotlin.databinding.ActivityUserBinding

class UserActivity:AppCompatActivity(R.layout.activity_user) {

    private lateinit var binding :ActivityUserBinding
    private val viewModel:UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        // 2. Quan sát LiveData từ ViewModel
        viewModel.allUsers.observe(this) { users ->
            adapter.setData(users)
        }

        binding.buttonAddUser.setOnClickListener {
            val firstName = binding.editFirstName.text.toString()
            val lastName = binding.editLastName.text.toString()
            val age = binding.editAge.text.toString().toIntOrNull()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && age != null) {
                val user = User(firstName = firstName, lastName = lastName, age = age)
                viewModel.addUser(user)
                binding.editFirstName.text.clear()
                binding.editLastName.text.clear()
                binding.editAge.text.clear()
            }
        }

    }


    }