package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.User
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.UserDao
import com.example.principal.codechallenge.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject

class UserViewModel @Inject constructor(private val repo: UserRepository, private val userDao: UserDao, private val executor: Executor): ViewModel(){

    private var last_searches: LiveData<List<UserDatabase>> = repo.getLast5Users()
    private var networkState = MutableLiveData<NetworkState>()
    private val disposables = CompositeDisposable()

    fun getLastSearches() = last_searches

    fun getNetworkState() = networkState

    fun getUser(username: String){

        disposables.add(repo.getUserServer(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            run {
                                val user: User =  Gson().fromJson(result, User::class.java)
                                val(lang, points) = getBestLanguage(user.ranks.getAsJsonObject("languages"))

                                executor.execute {
                                    userDao.insertUser(UserDatabase(user.username, user.name, user.leaderboardPosition, lang.capitalize(), points, System.currentTimeMillis()))
                                }
                                networkState.postValue(NetworkState("OK", ""))
                            }
                        },
                        { throwable -> networkState.postValue(NetworkState("ERROR", throwable.localizedMessage)) }
                ))
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