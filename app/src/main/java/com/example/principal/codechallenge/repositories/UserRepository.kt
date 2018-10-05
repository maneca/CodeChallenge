package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.principal.codechallenge.User
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.UserDao
import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class UserRepository @Inject constructor(private var webservices: Webservices, private var userDao: UserDao, private var executor: Executor){


    fun getLast5Users(): LiveData<List<UserDatabase>>{

        return userDao.getLastFive()
    }

    fun getUserFromServer(){

        webservices.getUser("g964").enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("JMF-error", t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    val user: User? = response.body()

                    val name = user?.name

                    val(lang, points) = getBestLanguage(user!!.ranks.getAsJsonObject("languages"))

                    val userDatabase = UserDatabase(user.username, name, user.leaderboardPosition, lang.capitalize(), points, System.currentTimeMillis())

                    executor.execute {
                        userDao.insertUser(userDatabase)
                    }

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