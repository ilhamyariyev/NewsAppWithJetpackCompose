package com.info.androidileriders2.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_news ORDER BY id DESC")
    fun getAllFavoriteItemsFlow(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)


}