package com.tkjen.basic_android_kotlin.permissions

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyCustomDialogFragment: DialogFragment() {

    interface MyDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Sử dụng listener này để truyền sự kiện về Activity/Fragment host
    var listener: MyDialogListener? = null

    companion object {
        fun newInstance(title: String, message: String): MyCustomDialogFragment {
            val fragment = MyCustomDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("message", message)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Gắn listener (Activity/Fragment host phải implement MyDialogListener)
        try {
            listener = targetFragment as? MyDialogListener ?: activity as MyDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((targetFragment?.toString() ?: activity.toString()) +
                    " phải implement MyDialogListener")
        }


        val title = arguments?.getString("title") ?: "Mặc định"
        val message = arguments?.getString("message") ?: "Nội dung mặc định."

        return AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, id ->
                listener?.onDialogPositiveClick(this)
            }
            .setNegativeButton("Cancel") { dialog, id ->
                listener?.onDialogNegativeClick(this)
            }
            .create()
    }
}