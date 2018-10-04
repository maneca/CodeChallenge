package com.example.principal.codechallenge.dependencyInjection

import com.example.principal.codechallenge.webservices.Webservices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module(includes = arrayOf(ViewModelModule::class))
class NetworkModule {

    @Singleton
    @Provides
    fun provideClientsWebservices(): Webservices {
        return Retrofit.Builder()
                .baseUrl("https://www.codewars.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Webservices::class.java)
    }

    @Singleton
    @Provides
    fun provideExecutor() : Executor{
        return Executors.newSingleThreadExecutor()
    }
    
}

