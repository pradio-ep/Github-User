package com.pradioep.githubuser.view.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pradioep.githubuser.R
import com.pradioep.githubuser.adapter.UserAdapter
import com.pradioep.githubuser.databinding.ActivityMainBinding
import com.pradioep.githubuser.helper.UtilityHelper
import com.pradioep.githubuser.model.Item
import com.pradioep.githubuser.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    private lateinit var userAdapter: UserAdapter
    private var query = ""
    private var page = 1
    private var per_page = 10

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
            listUser.observe(this@MainActivity, Observer {
                setListResult(it)
            })
            clickSearch.observe(this@MainActivity, Observer {
                search()
            })
        }
        setView()
    }

    override fun onStart() {
        super.onStart()
        setToolbar(getString(R.string.app_name))
    }

    private fun setView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(this@MainActivity, arrayListOf())
        rv_user.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        rv_user.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && recyclerView.canScrollVertically(1).not()) {
                    val listSize = viewModel.listUser.value?.size!!
                    val maxResult = viewModel.maxResult.value!!
                    if (listSize < maxResult) {
                        page += 1
                        viewModel.searchUser(query, page, per_page)
                    } else {
                        viewModel.snackbarMessage.value = getString(R.string.end_of_result)
                    }
                }
            }
        })
    }

    private fun search() {
        if (sv_text.query.isEmpty()) {
            sv_text.requestFocus()
            viewModel.snackbarMessage.value = getString(R.string.error_blank)
            return
        }
        sv_text.clearFocus()
        initRecyclerView()
        query = sv_text.query.toString()
        page = 1
        viewModel.isLoading.value = true
        viewModel.resetList()
        viewModel.searchUser(query, page, per_page)
    }

    private fun setListResult(list: ArrayList<Item>) {
        userAdapter.updateList(list)
        userAdapter.notifyDataSetChanged()
    }
}