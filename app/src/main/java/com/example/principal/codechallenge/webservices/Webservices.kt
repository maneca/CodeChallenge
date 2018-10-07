package com.example.principal.codechallenge.webservices

import com.example.principal.codechallenge.ApiResponse
import com.example.principal.codechallenge.CompletedChallanges
import com.example.principal.codechallenge.User
import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservices {

    @GET("users/{id_or_username}")
    fun getUser(@Path("id_or_username") id_or_username: String): Call<User>


    @GET("users/{id_or_username}/code-challenges/completed")
    fun getCompletedChallenges(@Path("id_or_username") id_or_username: String, @Query("page") page: Int): Call<CompletedChallanges>

    @GET("users/{id_or_username}/code-challenges/authored")
    fun getAuthoredChallenges(@Path("id_or_username") id_or_username: String): Call<ApiResponse>


    @GET("code-challenges/{id_or_slug}")
    fun getChallengeDetails(@Path("id_or_slug") id_or_slug: String): Observable<JsonElement>
}