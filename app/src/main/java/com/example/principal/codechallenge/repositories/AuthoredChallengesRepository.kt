package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.principal.codechallenge.ApiResponse
import com.example.principal.codechallenge.AuthoredChallenge
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.database.dao.AuthoredChallengeDao
import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject


class AuthoredChallengesRepository @Inject constructor(val services: Webservices, val executor: Executor, val challengeDao: AuthoredChallengeDao, val context: Context){

    fun getAuthoredChallenges(username: String, networkState: MutableLiveData<NetworkState>): LiveData<PagedList<AuthoredChallenge>>{

        val preferences = context.getSharedPreferences("My_Prefs_File", MODE_PRIVATE)
        val lastUsername = preferences.getString("username", "")

        if(lastUsername!! != username && !lastUsername.isEmpty()){

            executor.execute {

                if(challengeDao.getNrChallenges()>0){
                    challengeDao.deleteAll()
                }

                getAuthoredChallengesFromServer(username, networkState)
            }
        }else if (lastUsername.isEmpty())
            getAuthoredChallengesFromServer(username, networkState)

        val factory: DataSource.Factory<Int, AuthoredChallenge> = challengeDao.getAuthoredChallenges()

        val pagedListBuilder: LivePagedListBuilder<Int, AuthoredChallenge> = LivePagedListBuilder<Int, AuthoredChallenge>(factory, 10)


        val editor = preferences.edit()
        editor.putString("username", username)
        editor.apply()

        return pagedListBuilder.build()
    }

    private fun getAuthoredChallengesFromServer(username: String, networkState: MutableLiveData<NetworkState>){

        services.getAuthoredChallenges(username).enqueue(object: Callback<ApiResponse>{
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                networkState.postValue(NetworkState("ERROR", t.message.toString()))
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                if(response.isSuccessful){

                    val apiResponse: ApiResponse? = response.body()

                    val listChallenges = apiResponse!!.data

                    executor.execute {

                        challengeDao.insertAllChallenge(listChallenges)
                    }
                }else{
                    val parser = JsonParser()
                    val error = parser.parse(response.errorBody()!!.string()) as JsonObject

                    networkState.postValue(NetworkState("FAILED", error.get("reason").asString.capitalize()))
                }

            }
        })
    }
}