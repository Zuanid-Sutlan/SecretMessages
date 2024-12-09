package com.example.secretmessagesencrypter.precentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secretmessagesencrypter.data.model.Message
import com.example.secretmessagesencrypter.domain.repository.EncryptionRepository
import com.example.secretmessagesencrypter.domain.repository.MessageDatabaseRepoImpl
import com.example.secretmessagesencrypter.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.Key

class EncryptionViewModel(
    private val encryptionRepository: EncryptionRepository,
    private val messageDatabaseRepoImpl: MessageDatabaseRepoImpl
) : ViewModel() {

    private val key = Constants.DEFAULT_KEY

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val _encryptedText = MutableStateFlow("")
    val encryptedText: StateFlow<String> = _encryptedText

    private val _decryptedText = MutableStateFlow("")
    val decryptedText: StateFlow<String> = _decryptedText



    init {
        fetchAllMessages()
        var secretKey: Key = encryptionRepository.generateKey(key.toByteArray())

    }

    fun encryptText(input: String, encryptionKey: String = key) {
        viewModelScope.launch {
            _encryptedText.value = encryptionRepository.encrypt(input, encryptionKey.toByteArray())

            val message = Message(
                content = _encryptedText.value,
                timestamp = System.currentTimeMillis()
            )
            saveMessage(message)
        }
    }

    fun decryptText(input: String, encryptionKey: String) {
        viewModelScope.launch {
            _decryptedText.value = encryptionRepository.decrypt(input, encryptionKey.toByteArray())
        }
    }

    fun fetchAllMessages(){
        viewModelScope.launch {
            messageDatabaseRepoImpl.getAllMessages().collect{
                _messages.value = it
            }
        }
    }

    fun saveMessage(message: Message){
        viewModelScope.launch {
            messageDatabaseRepoImpl.upsertMessage(message)
        }
    }

    fun deleteMessage(message: Message){
        viewModelScope.launch {
            messageDatabaseRepoImpl.deleteMessage(message)
        }
    }
}
