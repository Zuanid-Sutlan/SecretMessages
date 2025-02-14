package com.example.secretmessagesencrypter.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.secretmessagesencrypter.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Upsert
    suspend fun upsertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message")
    fun getAllMessages(): Flow<List<Message>>

}