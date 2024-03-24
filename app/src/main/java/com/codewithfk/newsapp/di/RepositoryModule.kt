package com.codewithfk.newsapp.di

import com.codewithfk.newsapp.data.repository.NewsRepositoryImpl
import com.codewithfk.newsapp.data.web.NewApi
import com.codewithfk.newsapp.domain.respository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

}