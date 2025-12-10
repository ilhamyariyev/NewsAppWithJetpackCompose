package com.info.androidileriders2.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


//@Dao
//interface FavoriteDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(favoriteItem: Favorite)
//
//    @Delete
//    suspend fun delete(favoriteItem: Favorite)
//
//    @Query("SELECT * FROM favorite_news")
//    fun getAllFavoriteItemsFlow(): Flow<List<Favorite>>
//}