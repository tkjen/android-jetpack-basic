package com.tkjen.basic_android_kotlin.room

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.tkjen.basic_android_kotlin.R
import com.tkjen.basic_android_kotlin.databinding.ActivityUserBinding

class UserActivity:AppCompatActivity(R.layout.activity_user) {

    private lateinit var binding :ActivityUserBinding
    private val viewModel:UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private lateinit var listAdapter:UserListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)



      //  loadAdapter()
        loadListAdapter()



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

        private fun loadListAdapter() {
           listAdapter = UserListAdapter(
               onDeleteClick = {user ->
                   viewModel.deleteUser(user)
               }
           )
            binding.recyclerViewUsers.adapter = listAdapter
            binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)
            viewModel.allUsers.observe(this) { users ->
                listAdapter.submitList(users)
            }

        }

        private fun loadAdapter() {
            adapter = UserAdapter(onItemClick = { user ->
                // Ví dụ: mở dialog sửa/xoá
                viewModel.deleteUser(user)
                Toast.makeText(this,"Delete user: ${user.firstName} ",Toast.LENGTH_SHORT).show()
            })
            binding.recyclerViewUsers.adapter = adapter
            binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)
            // 2. Quan sát LiveData từ ViewModel
            viewModel.allUsers.observe(this) { users ->
                adapter.setData(users)
            }
        }


}