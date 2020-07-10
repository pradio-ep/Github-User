package com.pradioep.githubuser.di

import com.pradioep.githubuser.repository.MainRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { MainRepository(get()) }
}