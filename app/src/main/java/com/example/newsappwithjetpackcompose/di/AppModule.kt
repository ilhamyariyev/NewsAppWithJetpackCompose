package com.example.newsappwithjetpackcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.newsappwithjetpackcompose.data.api.NewsApi
import com.example.newsappwithjetpackcompose.util.Constants.BASE_URL
import com.info.androidileriders2.roomDB.AppDatabase
import com.info.androidileriders2.roomDB.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "news_db"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "appDataStore"
    )

    @Singleton
    @Provides
    fun provideAppDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
    }
}