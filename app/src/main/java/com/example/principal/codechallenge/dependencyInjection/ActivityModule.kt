package com.example.principal.codechallenge.dependencyInjection

import com.example.principal.codechallenge.ui.ChallengesActivity
import com.example.principal.codechallenge.ui.MainActivity
import com.example.principal.codechallenge.ui.SearchableActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class  ActivityModule {


    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMemberUiActivity(): ChallengesActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchableActivity(): SearchableActivity

}