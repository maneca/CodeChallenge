package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.principal.codechallenge.Challenge
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.repositories.AuthoredChallengesRepository
import com.example.principal.codechallenge.repositories.CompletedChallengeDataFactory
import com.example.principal.codechallenge.webservices.Webservices
import java.util.concurrent.Executor
import javax.inject.Inject


class ChallengesViewModel @Inject constructor(private var executor: Executor, private var services: Webservices, private var repo: AuthoredChallengesRepository): ViewModel(){

    private lateinit var completedChallanges: LiveData<PagedList<Challenge>>
    private lateinit var networkState: LiveData<NetworkState>
    private val authoredNetworkState: MutableLiveData<NetworkState> = MutableLiveData()

    fun initCompletedChallenge(username: String) {

        val feedDataFactory = CompletedChallengeDataFactory(services,username)

        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData()) {
            dataSource -> dataSource.getNetworkState() }

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build()

        completedChallanges = LivePagedListBuilder(feedDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()


    }


    fun getCompletedChallenges() = completedChallanges

    fun getAuthoredChallenges(username: String) = repo.getAuthoredChallenges(username, authoredNetworkState)

    fun getNetworkState() = networkState

    fun getAuthoredNetworkState() = authoredNetworkState


}