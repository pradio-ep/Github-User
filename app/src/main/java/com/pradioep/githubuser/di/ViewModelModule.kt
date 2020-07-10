package com.pradioep.githubuser.di

import com.pradioep.githubuser.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
}