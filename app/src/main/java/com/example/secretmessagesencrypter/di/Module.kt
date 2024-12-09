package com.example.secretmessagesencrypter.di

import androidx.room.Room
import com.example.secretmessagesencrypter.data.db.AppDatabase
import com.example.secretmessagesencrypter.domain.repository.EncryptionRepository
import com.example.secretmessagesencrypter.domain.repository.EncryptionRepositoryImpl
import com.example.secretmessagesencrypter.domain.repository.MessageDatabaseRepoImpl
import com.example.secretmessagesencrypter.precentation.screen.EncryptionViewModel
import com.example.secretmessagesencrypter.precentation.screen.settings.SettingsViewModel
import com.example.secretmessagesencrypter.utils.SettingsManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {

    val appModule = module {

        // Provide AppDatabase
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                "app_database"
            ).build()
        }

        // Provide DAO
        single { get<AppDatabase>().messageDao() }

        // Provide setting datastore
        single { SettingsManager(androidContext()) }

        // Provide database Repository
        single { MessageDatabaseRepoImpl(get()) }

        // Provide encryption decryption repo
        single<EncryptionRepository> { EncryptionRepositoryImpl() }

        // Provide encryption/decryption viewModel
        viewModel { EncryptionViewModel(get(), get()) }

        // Provide settings viewModel
        viewModel { SettingsViewModel(get()) }

    }

}