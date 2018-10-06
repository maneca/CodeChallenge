package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.CompletedChallanges
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletedChallengeDataSource constructor(private var services: Webservices, private var username: String): PageKeyedDataSource<Int, Challenge>() {

    val networkstate = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Challenge>) {

        services.getCompletedChallenges(username, 0).enqueue(object: Callback<CompletedChallanges>{
            override fun onFailure(call: Call<CompletedChallanges>, t: Throwable) {

                networkstate.postValue(NetworkState("ERROR", t.toString()))
            }

            override fun onResponse(call: Call<CompletedChallanges>, response: Response<CompletedChallanges>) {

                val completedChallanges: CompletedChallanges? = response.body()

                if(response.isSuccessful) {
                    callback.onResult(completedChallanges!!.data, null, 1)
                }else{

                    val parser = JsonParser()

                    val error = parser.parse(response.errorBody()!!.string()) as JsonObject

                    networkstate.postValue(NetworkState("FAILED", error.get("reason").asString.capitalize()))
                }
            }


        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Challenge>) {

        services.getCompletedChallenges(username, params.key).enqueue(object: Callback<CompletedChallanges>{
            override fun onFailure(call: Call<CompletedChallanges>, t: Throwable) {

                networkstate.postValue(NetworkState("ERROR", t.toString()))
            }

            override fun onResponse(call: Call<CompletedChallanges>, response: Response<CompletedChallanges>) {

                val completedChallanges: CompletedChallanges? = response.body()

                if(response.isSuccessful) {
                    val nextKey = if (params.key == completedChallanges!!.totalItems) null else params.key + 1
                    callback.onResult(completedChallanges.data, nextKey)
                }else{
                    val parser = JsonParser()
                    val error = parser.parse(response.errorBody()!!.string()) as JsonObject

                    networkstate.postValue(NetworkState("FAILED", error.get("reason").asString.capitalize()))
                }
            }

        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Challenge>) {
    }


    fun getNetworkState() = networkstate
}