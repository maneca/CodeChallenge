package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.principal.codechallenge.User
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.UserDao
import com.example.principal.codechallenge.webservices.Webservices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import java.util.concurrent.Callable
import java.util.concurrent.Executor

class UserRepository @Inject constructor(private var webservices: Webservices, private var userDao: UserDao, private var executor: Executor){


    fun getLast5Users(): LiveData<List<UserDatabase>>{

        executor.execute {
            Log.d("JMF", userDao.getNumber().toString())
        }

        return userDao.getLastFive()
    }

    fun getUserFromServer(){

        webservices.getUser("Unnamed").enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("User-error", t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    val user: User? = response.body()

                    val name = user?.name

                    val userDatabase = UserDatabase(user!!.username, name, user.leaderboardPosition, "java", 122, System.currentTimeMillis())

                    executor.execute {
                        userDao.insertUser(userDatabase)
                    }

                    Log.d("User-JMF", user.toString())
                }
            }

        })
    }
}