package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.webservices.Webservices


class CompletedChallengeDataFactory constructor(private var services: Webservices, private var username: String): DataSource.Factory<Int, Challenge>() {

    private var mutableLiveData = MutableLiveData<CompletedChallengeDataSource>()
    private lateinit var dataSourceCompleted: CompletedChallengeDataSource


    override fun create(): DataSource<Int, Challenge> {

        dataSourceCompleted = CompletedChallengeDataSource(services, username)

        mutableLiveData.postValue(dataSourceCompleted)
        return dataSourceCompleted
    }

    fun getMutableLiveData() = mutableLiveData
}