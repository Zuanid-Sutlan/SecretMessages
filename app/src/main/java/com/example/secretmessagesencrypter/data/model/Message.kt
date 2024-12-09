package com.example.secretmessagesencrypter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    val sender: String = "",
    val recipient: String = "",
    val content: String,
    val timestamp: Long,
    val key: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)