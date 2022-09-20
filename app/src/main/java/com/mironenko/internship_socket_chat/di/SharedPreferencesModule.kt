package com.mironenko.internship_socket_chat.di

import android.content.Context
import android.content.SharedPreferences
import com.mironenko.internship_socket_chat.util.PREFERENCE_FILE_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE
        )
    }
}