package com.pradioep.githubuser.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.pradioep.githubuser.model.Item
import com.pradioep.githubuser.repository.MainRepository
import com.pradioep.githubuser.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    var result = MutableLiveData<String>()
    var listUser = MutableLiveData<ArrayList<Item>>()

    fun searchUser (q: String, page: Int, per_page: Int) {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = mainRepository.searchUsers(q, page, per_page)) {
                is NetworkResponse.Success -> {
                    isLoading.value = false
                    result.value = response.body.items.toString()
                    listUser.value = response.body.items
                }
                is NetworkResponse.ServerError -> {
                    isLoading.value = false
                    snackbarMessage.value = response.body?.message
                }
                is NetworkResponse.NetworkError -> {
                    isLoading.value = false
                }
            }
        }
    }
}