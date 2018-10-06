package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.principal.codechallenge.ChallengeDetails
import com.example.principal.codechallenge.repositories.ChallengeDetailsRepository
import javax.inject.Inject

class ChallengeDetailsViewModel @Inject constructor(val repository: ChallengeDetailsRepository): ViewModel() {

    private var details: MutableLiveData<ChallengeDetails> = MutableLiveData()


    fun getChallendeDetails(id: String): LiveData<ChallengeDetails>{

        repository.getChalllengeDetails(id, details)

        return details
    }
}