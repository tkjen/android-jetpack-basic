package com.tkjen.basic_android_kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tkjen.basic_android_kotlin.R
import com.tkjen.basic_android_kotlin.data.MyItem

class MyAdapter(private val itemList: List<MyItem>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // ViewHolder: Giữ các view của một item
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val textView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    // Tạo ViewHolder mới (được gọi bởi LayoutManager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    // Gắn dữ liệu vào ViewHolder (được gọi bởi LayoutManager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.imageView.setImageResource(currentItem.images)
        holder.textView.text = currentItem.text

        // Xử lý sự kiện click item (ví dụ)
        holder.itemView.setOnClickListener {
            // Xử lý khi item được click
        }
    }

    // Trả về tổng số item
    override fun getItemCount() = itemList.size
}