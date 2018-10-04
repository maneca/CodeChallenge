package com.example.principal.codechallenge.dependencyInjection

import android.app.Application
import com.example.principal.codechallenge.CodeChallengeApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ActivityModule::class, NetworkModule::class, DatabaseModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: CodeChallengeApp)
}