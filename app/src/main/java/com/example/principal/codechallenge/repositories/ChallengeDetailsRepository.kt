package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.MutableLiveData
import com.example.principal.codechallenge.ChallengeDetails
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChallengeDetailsRepository @Inject constructor(private val services: Webservices){


    fun getChalllengeDetails(id: String, details: MutableLiveData<ChallengeDetails>, networkState: MutableLiveData<NetworkState>){


        services.getChallengeDetails(id).enqueue(object : Callback<ChallengeDetails> {
            override fun onFailure(call: Call<ChallengeDetails>, t: Throwable) {

                networkState.postValue(NetworkState("ERROR", t.message.toString()))
            }

            override fun onResponse(call: Call<ChallengeDetails>, response: Response<ChallengeDetails>) {

                if(response.isSuccessful){

                    val challengeDetails: ChallengeDetails? = response.body()

                    details.postValue(challengeDetails!!)
                }else{
                    val parser = JsonParser()
                    val error = parser.parse(response.errorBody()!!.string()) as JsonObject

                    networkState.postValue(NetworkState("FAILED", error.get("reason").asString.capitalize()))
                }
            }

        })

    }
}