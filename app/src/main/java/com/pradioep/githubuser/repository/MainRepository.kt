package com.pradioep.githubuser.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.pradioep.githubuser.model.ResponseError
import com.pradioep.githubuser.model.ResponseSuccess
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MainService {

    @GET("search/users")
    @Headers("Authorization: token 73593f947a6fd455858fd46e44c8241e279190b0")
    suspend fun searchUsers(@Query("q") q: String,
                       @Query("page") page: Int,
                       @Query("per_page") per_page: Int)
            : NetworkResponse<ResponseSuccess, ResponseError>

}

open class MainRepository(private val mainService: MainService) {

    suspend fun searchUsers(q: String, page: Int, per_page: Int): NetworkResponse<ResponseSuccess, ResponseError> {
        return mainService.searchUsers(q, page, per_page)
    }

}