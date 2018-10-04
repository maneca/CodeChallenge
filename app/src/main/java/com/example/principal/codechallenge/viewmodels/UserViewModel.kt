package com.example.principal.codechallenge.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.repositories.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(var repo: UserRepository): ViewModel(){

    var last_searches: LiveData<List<UserDatabase>> = repo.getLast5Users()

    fun getUser() = repo.getUserFromServer()

    fun getLastSearches() = last_searches
}