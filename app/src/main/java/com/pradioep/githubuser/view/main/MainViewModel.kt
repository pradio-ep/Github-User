package com.pradioep.githubuser.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.pradioep.githubuser.model.Item
import com.pradioep.githubuser.repository.MainRepository
import com.pradioep.githubuser.util.SingleLiveEvent
import com.pradioep.githubuser.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    val isNotFoundVisible = MutableLiveData<Boolean>().apply { value = false }
    val isListUserVisible = MutableLiveData<Boolean>().apply { value = false }
    val maxResult = MutableLiveData<Int>()
    val listUser = MutableLiveData<ArrayList<Item>>().apply { value = arrayListOf() }
    val clickSearch = SingleLiveEvent<Unit>()

    fun searchUser (q: String, page: Int, per_page: Int) {
        viewModelScope.launch {
            when (val response = mainRepository.searchUsers(q, page, per_page)) {
                is NetworkResponse.Success -> {
                    isLoading.value = false

                    val totalCount = response.body.total_count
                    val items = response.body.items

                    if (totalCount == 0) {
                        isNotFoundVisible.value = true
                        isListUserVisible.value = false
                    } else {
                        isNotFoundVisible.value = false
                        isListUserVisible.value = true
                        updateList(totalCount, items)
                    }
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.body?.message
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.error.message
                }
            }
        }
    }

    private fun updateList(totalCount: Int, list: ArrayList<Item>) {
        maxResult.value = totalCount
        listUser.value?.addAll(list)
        listUser.value = listUser.value
    }

    fun resetList() {
        maxResult.value = 0
        listUser.value?.clear()
        listUser.value = listUser.value
    }

    fun onClickSearch() {
        hideKeyBoard.call()
        clickSearch.call()
    }
}