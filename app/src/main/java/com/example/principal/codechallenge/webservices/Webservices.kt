package com.example.principal.codechallenge.webservices

import com.example.principal.codechallenge.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Webservices {

    @GET("users/{id_or_username}")
    fun getUser(@Path("id_or_username") id_or_username: String): Call<User>
}