package com.pradioep.githubuser.view.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pradioep.githubuser.R
import kotlinx.android.synthetic.main.toolbar.*

open class BaseActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog()
    }

    fun setToolbar(title: String) {
        toolbar_title.text = title
    }

    private fun initDialog() {
        dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_waiting)
        dialog.setCancelable(false)
    }

    fun showWaitingDialog() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun hideWaitingDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}