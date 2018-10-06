package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.User
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.UserDao
import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class UserRepository @Inject constructor(private var webservices: Webservices, private var userDao: UserDao, private var executor: Executor){


    fun getLast5Users(): LiveData<List<UserDatabase>>{

        return userDao.getLastFive()
    }

    fun getUserFromServer(username: String, networkState: MutableLiveData<NetworkState>){

        webservices.getUser(username).enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {

                networkState.postValue(NetworkState("ERROR", t.message.toString()))
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    networkState.postValue(NetworkState("OK", ""))

                    val user: User? = response.body()

                    val name = user?.name

                    val(lang, points) = getBestLanguage(user!!.ranks.getAsJsonObject("languages"))

                    val userDatabase = UserDatabase(user.username, name, user.leaderboardPosition, lang.capitalize(), points, System.currentTimeMillis())

                    executor.execute {
                        userDao.insertUser(userDatabase)
                    }
                }else{
                    val parser = JsonParser()
                    val error = parser.parse(response.errorBody()!!.string()) as JsonObject

                    networkState.postValue(NetworkState("FAILED", error.get("reason").asString.capitalize()))
                }
            }

        })
    }

    private fun getBestLanguage(languages: JsonObject): Pair<String, Int>{

        var lang = ""
        var points = 0

        for(key in languages.keySet()){

            if(languages.getAsJsonObject(key).get("score").asInt > points){
                points = languages.getAsJsonObject(key).get("score").asInt
                lang = key
            }
        }

        return Pair(lang, points)
    }
}