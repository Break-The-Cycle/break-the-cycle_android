package kau.brave.breakthecycle.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kau.brave.breakthecycle.data.database.BraveDatabase
import kau.brave.breakthecycle.utils.Constants.BRAVE_DATASTORE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(BRAVE_DATASTORE) },
        )

    @Singleton
    @Provides
    fun provideBraveDatabase(
        @ApplicationContext context: Context
    ): BraveDatabase {
        return BraveDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideAddressDao(database: BraveDatabase) = database.addressDao()

}
