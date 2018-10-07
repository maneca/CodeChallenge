package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.LiveData
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.UserDao
import com.example.principal.codechallenge.webservices.Webservices
import com.google.gson.JsonElement
import io.reactivex.Observable
import javax.inject.Inject

class UserRepository @Inject constructor(private var webservices: Webservices, private var userDao: UserDao){


    fun getLast5Users(): LiveData<List<UserDatabase>>{

        return userDao.getLastFive()
    }


    fun getUserServer(username: String): Observable<JsonElement>{

        return webservices.getUser(username)
    }

}