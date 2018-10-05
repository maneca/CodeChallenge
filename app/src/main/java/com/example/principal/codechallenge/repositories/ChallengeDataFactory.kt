package com.example.principal.codechallenge.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.webservices.Webservices


class ChallengeDataFactory constructor(private var services: Webservices, private var username: String): DataSource.Factory<Int, Challenge>() {

    private var mutableLiveData = MutableLiveData<ChallengeDataSource>()
    private lateinit var dataSource: ChallengeDataSource


    override fun create(): DataSource<Int, Challenge> {

        dataSource = ChallengeDataSource(services, username)

        mutableLiveData.postValue(dataSource)
        return dataSource
    }

    //fun getLiveData() = mutableLiveData
}