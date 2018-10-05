package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.repositories.ChallengeDataFactory
import com.example.principal.codechallenge.webservices.Webservices
import java.util.concurrent.Executor
import javax.inject.Inject


class ChallengesViewModel @Inject constructor(var executor: Executor, var services: Webservices): ViewModel(){

    private lateinit var completedChallanges: LiveData<PagedList<Challenge>>

    fun init(username: String) {

        val feedDataFactory = ChallengeDataFactory(services,username)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build()

        completedChallanges = LivePagedListBuilder(feedDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()


    }

    fun getCompletedChallenges() = completedChallanges

}