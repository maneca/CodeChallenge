package com.example.principal.codechallenge.dependencyInjection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.principal.codechallenge.viewmodels.ChallengeDetailsViewModel
import com.example.principal.codechallenge.viewmodels.ChallengesViewModel
import com.example.principal.codechallenge.viewmodels.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindJokeViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChallengesViewModel::class)
    abstract fun bindChallengesViewModel(challengesViewModel: ChallengesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChallengeDetailsViewModel::class)
    abstract fun bindChallengesDetailsViewModel(challengeDetailsViewModel: ChallengeDetailsViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory
}