package com.example.principal.codechallenge.dependencyInjection

import com.example.principal.codechallenge.ui.ChallengesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeChallengesFragment(): ChallengesFragment

}