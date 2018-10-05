package com.example.principal.codechallenge.repositories

import android.arch.paging.PageKeyedDataSource
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.CompletedChallanges
import com.example.principal.codechallenge.webservices.Webservices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletedChallengeDataSource constructor(private var services: Webservices, private var username: String): PageKeyedDataSource<Int, Challenge>() {


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Challenge>) {

        services.getCompletedChallenges(username, 0).enqueue(object: Callback<CompletedChallanges>{
            override fun onFailure(call: Call<CompletedChallanges>, t: Throwable) {

            }

            override fun onResponse(call: Call<CompletedChallanges>, response: Response<CompletedChallanges>) {

                val completedChallanges: CompletedChallanges? = response.body()

                if(response.isSuccessful) {
                    callback.onResult(completedChallanges!!.data, null, 1)
                }
            }


        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Challenge>) {

        services.getCompletedChallenges(username, params.key).enqueue(object: Callback<CompletedChallanges>{
            override fun onFailure(call: Call<CompletedChallanges>, t: Throwable) {

            }

            override fun onResponse(call: Call<CompletedChallanges>, response: Response<CompletedChallanges>) {

                val completedChallanges: CompletedChallanges? = response.body()

                if(response.isSuccessful) {
                    val nextKey = if (params.key == completedChallanges!!.totalItems) null else params.key + 1
                    callback.onResult(completedChallanges.data, nextKey)
                }
            }

        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Challenge>) {
    }
}