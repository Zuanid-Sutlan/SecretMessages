package com.example.secretmessagesencrypter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.secretmessagesencrypter.data.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessagesDao
}