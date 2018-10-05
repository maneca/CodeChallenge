package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.example.principal.codechallenge.ApiResponse
import com.example.principal.codechallenge.AuthoredChallenge
import com.example.principal.codechallenge.database.dao.AuthoredChallengeDao
import com.example.principal.codechallenge.webservices.Webservices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class AuthoredChallengesRepository @Inject constructor(var services: Webservices, var executor: Executor, var challengeDao: AuthoredChallengeDao){

    fun getAuthoredChallenges(username: String): LiveData<PagedList<AuthoredChallenge>>{

        executor.execute {

            if(challengeDao.getNrChallenges()>0){
                challengeDao.deleteAll()
            }
        }

        getAuthoredChallengesFromServer(username)

        val factory: DataSource.Factory<Int, AuthoredChallenge> = challengeDao.getAuthoredChallenges()

        val pagedListBuilder: LivePagedListBuilder<Int, AuthoredChallenge> = LivePagedListBuilder<Int, AuthoredChallenge>(factory, 10)

        return pagedListBuilder.build()
    }

    private fun getAuthoredChallengesFromServer(username: String){

        services.getAuthoredChallenges(username).enqueue(object: Callback<ApiResponse>{
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("JMF-error", t.toString())
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                val apiResponse: ApiResponse? = response.body()



                executor.execute {

                    challengeDao.insertAllChallenge(apiResponse!!.data)
                }
            }
        })
    }
}