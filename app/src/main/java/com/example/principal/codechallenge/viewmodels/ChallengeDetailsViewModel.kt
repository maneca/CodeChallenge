package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.principal.codechallenge.ChallengeDetails
import com.example.principal.codechallenge.NetworkState
import com.example.principal.codechallenge.repositories.ChallengeDetailsRepository
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ChallengeDetailsViewModel @Inject constructor(val repository: ChallengeDetailsRepository): ViewModel() {

    private var details: MutableLiveData<ChallengeDetails> = MutableLiveData()
    private var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private val disposables = CompositeDisposable()


    fun getNetworkState() = networkState

    fun getDetails() = details

    fun getChallengeDetailsTest(id: String){

        disposables.add(repository.getChallengeDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> details.postValue(Gson().fromJson(result, ChallengeDetails::class.java)) },
                        { throwable -> networkState.postValue(NetworkState("ERROR", throwable.localizedMessage)) }
                ))
    }
}