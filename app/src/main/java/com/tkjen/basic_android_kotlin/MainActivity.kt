package com.tkjen.basic_android_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tkjen.basic_android_kotlin.adapter.MyAdapter
import com.tkjen.basic_android_kotlin.data.MyItem
import com.tkjen.basic_android_kotlin.databinding.ActivityMainBinding
import com.tkjen.basic_android_kotlin.permissions.MyCustomDialogFragment
import com.tkjen.basic_android_kotlin.permissions.PermissionActivity

class MainActivity : AppCompatActivity(), MyCustomDialogFragment.MyDialogListener {

    private lateinit var binding: ActivityMainBinding
    private val PREF_NAME = "my_preferences"
    private val KEY_USERNAME = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.textViewSample.text = "Xin chào!"



        binding.buttonSample.setOnClickListener {
            val name = binding.editTextSample.text.toString()
            saveUsername(name)
            binding.progressBarSample.progress += 10
         }

        binding.checkBoxSample.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
             Log.d("CheckBox", "Đã đồng ý")
             } else {
             Log.d("CheckBox", "Chưa đồng ý")
         }
     }
        binding.btnPermission.setOnClickListener{
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)

        }
        binding.btnLoad.setOnClickListener {
            val username = loadUsername()
            binding.textViewSample.text = "Username: $username"
        }
        binding.btnShowDialog.setOnClickListener {
            val dialog = MyCustomDialogFragment.newInstance(
                title = "Thông báo",
                message = "Bạn có chắc muốn tiếp tục?"
            )
            dialog.show(supportFragmentManager, "MyCustomDialog")
        }

        setupRecyclerView()


    }
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        Toast.makeText(this, "Đã nhấn OK", Toast.LENGTH_SHORT).show()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Toast.makeText(this, "Đã nhấn Cancel", Toast.LENGTH_SHORT).show()
    }


    private fun setupRecyclerView() {
        val itemList = ArrayList<MyItem>()
        itemList.add(MyItem(R.drawable.ic_launcher_foreground, "Item 1"))
        itemList.add(MyItem(R.drawable.ic_launcher_foreground, "Item 2"))
        itemList.add(MyItem(R.drawable.ic_launcher_foreground, "Item 3"))
        itemList.add(MyItem(R.drawable.ic_launcher_foreground, "Item 4"))
        itemList.add(MyItem(R.drawable.ic_launcher_foreground, "Item 5"))
        val adapter = MyAdapter(itemList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun saveUsername(username: String) {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString(KEY_USERNAME, username)
            .apply()
    }

    // Đọc dữ liệu từ SharedPreferences
    private fun loadUsername(): String? {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY_USERNAME, "Chưa có username")
    }
}