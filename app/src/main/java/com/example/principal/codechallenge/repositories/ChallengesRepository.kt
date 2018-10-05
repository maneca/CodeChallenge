package com.example.principal.codechallenge.repositories

import android.util.Log
import com.example.principal.codechallenge.CompletedChallanges
import com.example.principal.codechallenge.webservices.Webservices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChallengesRepository @Inject constructor(private var services: Webservices){


    fun getChallenges(){


        services.getCompletedChallenges("Voile", 0).enqueue(object: Callback<CompletedChallanges>{
            override fun onFailure(call: Call<CompletedChallanges>, t: Throwable) {
                Log.d("JMF-error", t.toString())
            }

            override fun onResponse(call: Call<CompletedChallanges>, response: Response<CompletedChallanges>) {

                if (response.isSuccessful) {

                    val completedChallanges: CompletedChallanges? = response.body()

                    Log.d("JMF-completed", completedChallanges.toString())
                }

            }


        })
    }
}