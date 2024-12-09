package com.example.secretmessagesencrypter.domain.repository

import com.example.secretmessagesencrypter.data.db.MessagesDao
import com.example.secretmessagesencrypter.data.model.Message

class MessageDatabaseRepoImpl(private val dao: MessagesDao) {

    suspend fun upsertMessage(message: Message) = dao.upsertMessage(message)

    suspend fun deleteMessage(message: Message) = dao.deleteMessage(message)

    fun getAllMessages() = dao.getAllMessages()


}