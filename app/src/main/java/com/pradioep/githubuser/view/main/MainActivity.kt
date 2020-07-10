package com.pradioep.githubuser.view.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.pradioep.githubuser.R
import com.pradioep.githubuser.databinding.ActivityMainBinding
import com.pradioep.githubuser.helper.UtilityHelper
import com.pradioep.githubuser.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        with(viewModel) {
            hideKeyBoard.observe(this@MainActivity, Observer {
                UtilityHelper.hideSoftKeyboard(this@MainActivity)
            })
            snackbarMessage.observe(this@MainActivity, Observer {
                when (it) {
                    is Int -> UtilityHelper.snackbarLong(view_parent, getString(it))
                    is String -> UtilityHelper.snackbarLong(view_parent, it)
                }
            })
            isLoading.observe(this@MainActivity, Observer { bool ->
                bool.let { loading ->
                    if(loading){ showWaitingDialog() }
                    else { hideWaitingDialog() }
                }
            })
        }

        setView()
    }

    override fun onStart() {
        super.onStart()
        setToolbar(getString(R.string.app_name))
    }

    private fun setView() {
        viewModel.searchUser("google", 1, 1)
    }
}
