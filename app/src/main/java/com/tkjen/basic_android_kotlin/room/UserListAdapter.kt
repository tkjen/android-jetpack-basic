package com.tkjen.basic_android_kotlin.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tkjen.basic_android_kotlin.databinding.ItemUserBinding

class UserListAdapter(private val onDeleteClick: (User) -> Unit) :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserDiffCallback){
    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewName.text = "${user.firstName} ${user.lastName}"
            binding.textViewAge.text = "Age: ${user.age}"

            binding.btnDelete.setOnClickListener {
                onDeleteClick(user)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}