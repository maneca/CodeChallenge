package com.example.principal.codechallenge.dependencyInjection

import com.example.principal.codechallenge.ui.AuthoredChallengesFragment
import com.example.principal.codechallenge.ui.CompletedChallengesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeCompletedChallengesFragment(): CompletedChallengesFragment

    @ContributesAndroidInjector
    internal abstract fun contributeAuthoredChallengesFragment(): AuthoredChallengesFragment

}