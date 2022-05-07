package com.example.catfacts.di

import com.example.catfacts.common.Constants
import com.example.catfacts.data.remote.CatFactsApi
import com.example.catfacts.data.repository.CatFactsRepositoryImpl
import com.example.catfacts.domain.repository.CatFactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatFactsApi(): CatFactsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatFactsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCatFactRepository(api: CatFactsApi): CatFactsRepository {
        return CatFactsRepositoryImpl(api)
    }
}