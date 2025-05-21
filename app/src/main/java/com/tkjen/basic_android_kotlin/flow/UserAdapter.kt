package com.tkjen.basic_android_kotlin.flow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tkjen.basic_android_kotlin.R

class UserAdapter(
    private var users: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.userName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_flow, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.nameText.text = user.name
        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }

    fun submitList(newList: List<User>) {
        users = newList
        notifyDataSetChanged()
    }
}