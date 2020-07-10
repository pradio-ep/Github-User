package com.pradioep.githubuser.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.pradioep.githubuser.R

class UtilityHelper {

    companion object {

        fun snackbarLong(view: View, text: String) {
            val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("OK", View.OnClickListener { })

            val viewSnackbar = snackbar.view
            val textView =
                viewSnackbar.findViewById(R.id.snackbar_text) as TextView
            textView.maxLines = 5
            textView.setPadding(0, 0, 0, 0)

            snackbar.show()
        }

        fun hideSoftKeyboard(activity: Activity) {
            val mgr = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}