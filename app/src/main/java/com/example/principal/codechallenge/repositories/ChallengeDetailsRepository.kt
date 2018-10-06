package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.MutableLiveData
import com.example.principal.codechallenge.ChallengeDetails
import com.example.principal.codechallenge.webservices.Webservices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChallengeDetailsRepository @Inject constructor(private val services: Webservices){


    fun getChalllengeDetails(id: String, details: MutableLiveData<ChallengeDetails>){


        services.getChallengeDetails(id).enqueue(object : Callback<ChallengeDetails> {
            override fun onFailure(call: Call<ChallengeDetails>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ChallengeDetails>, response: Response<ChallengeDetails>) {

                if(response.isSuccessful){

                    val challengeDetails: ChallengeDetails? = response.body()

                    details.postValue(challengeDetails!!)
                }
            }

        })

    }
}