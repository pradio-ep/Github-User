package com.pradioep.githubuser.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pradioep.githubuser.util.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val snackbarMessage : MutableLiveData<Any> = MutableLiveData()
    val hideKeyBoard: SingleLiveEvent<Unit> = SingleLiveEvent()
}