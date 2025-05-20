package com.tkjen.basic_android_kotlin.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tkjen.basic_android_kotlin.databinding.ItemUserBinding

class UserAdapter(
    private var users: List<User> = listOf()
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewName.text = "${user.firstName} ${user.lastName}"
            binding.textViewAge.text = "Age: ${user.age}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun setData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
