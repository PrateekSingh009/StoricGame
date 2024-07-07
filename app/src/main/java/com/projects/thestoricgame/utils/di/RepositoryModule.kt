package com.projects.thestoricgame.utils.di

import com.projects.thestoricgame.main.api.ApiService
import com.projects.thestoricgame.main.data.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(api : ApiService) : ListRepository {
        return ListRepository(api)
    }
}