package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.principal.codechallenge.CompletedChallanges
import com.example.principal.codechallenge.repositories.ChallengesRepository
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(private var repository: ChallengesRepository): ViewModel(){

    private var completedChallanges = MutableLiveData<List<CompletedChallanges>>()

    init {
        repository.getChallenges()
    }

    fun getCompletedChallenges() = completedChallanges

}