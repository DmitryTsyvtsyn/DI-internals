package io.github.dmitrytsyvtsyn.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {

    @Query("select * from post_table order by date_time")
    suspend fun fetch(): List<PostEntity>

    @Query("select * from post_table where id == :id")
    suspend fun fetch(id: Long): PostEntity

    @Query("delete from post_table where id == :id")
    suspend fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PostEntity)

}