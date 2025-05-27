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
    fun provideDatabase(app: Application): ToDoDatabase {
        return Room.databaseBuilder(app, ToDoDatabase::class.java, "todo.db").build()
    }

    @Provides
    fun provideDao(db: ToDoDatabase): ToDoDao = db.todoDao()

    @Provides
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://example.com/") // hardcoded
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideRepository(dao: ToDoDao, api: ApiService): ToDoRepository =
        ToDoRepository(dao, api) // no abstraction
}