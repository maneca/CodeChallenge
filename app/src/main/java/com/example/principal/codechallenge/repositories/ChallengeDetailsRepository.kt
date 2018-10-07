package com.example.principal.codechallenge.repositories

import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonElement
import io.reactivex.Observable
import javax.inject.Inject

class ChallengeDetailsRepository @Inject constructor(private val services: Webservices){


    fun getChallengeDetails(id: String): Observable<JsonElement>{

        return services.getChallengeDetails(id)
    }
}