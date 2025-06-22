package com.example.coderefactoring.di

import android.app.Application
import androidx.room.Room
import com.example.coderefactoring.data.local.ToDoDao
import com.example.coderefactoring.data.local.ToDoDatabase
import com.example.coderefactoring.data.remote.ApiService
import com.example.coderefactoring.repository.ToDoRepository
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
    fun provideDatabase(application : Application): ToDoDatabase {
        return Room.databaseBuilder(application, ToDoDatabase::class.java, "todo.db").build()
    }

    @Provides
    fun provideDao(db: ToDoDatabase): ToDoDao = db.todoDao()

    @Provides
    fun provideBaseUrl(): String = "https://example.com/"

    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


    @Provides
    fun provideRepository(dao: ToDoDao, api: ApiService): ToDoRepository =
        ToDoRepository(dao, api) // no abstraction
}