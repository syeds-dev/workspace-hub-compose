package com.syedsubahani.workspacehub.di

import com.syedsubahani.workspacehub.data.remote.ContactsApi
import com.syedsubahani.workspacehub.data.repository.ContactsRepositoryImpl
import com.syedsubahani.workspacehub.repository.ContactsRepository
import com.syedsubahani.workspacehub.data.remote.RoomsApi
import com.syedsubahani.workspacehub.data.repository.RoomsRepositoryImpl
import com.syedsubahani.workspacehub.repository.RoomsRepository
import com.syedsubahani.workspacehub.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContactsApi(): ContactsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ContactsApi::class.java)

    @Singleton
    @Provides
    fun provideContactsRepository(api: ContactsApi): ContactsRepository = ContactsRepositoryImpl(api)


    @Singleton
    @Provides
    fun provideRoomsApi(): RoomsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RoomsApi::class.java)

    @Singleton
    @Provides
    fun provideRoomsRepository(api: RoomsApi): RoomsRepository = RoomsRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}