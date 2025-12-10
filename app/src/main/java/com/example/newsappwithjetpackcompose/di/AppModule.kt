package com.example.newsappwithjetpackcompose.di

import com.example.newsappwithjetpackcompose.data.api.NewsApi
import com.example.newsappwithjetpackcompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(
//            context, AppDatabase::class.java, "news_db"
//        ).fallbackToDestructiveMigration(false).build()
//    }
//
//    @Provides
//    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()
}