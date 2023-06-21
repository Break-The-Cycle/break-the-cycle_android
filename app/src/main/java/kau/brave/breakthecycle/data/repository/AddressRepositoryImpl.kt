package kau.brave.breakthecycle.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kau.brave.breakthecycle.data.database.AddressDao
import kau.brave.breakthecycle.data.database.AddressRepository
import kau.brave.breakthecycle.domain.model.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao,
    private val preferenceDataStore: DataStore<Preferences>,
) : AddressRepository {

    override fun insert(address: Address) {
        addressDao.insert(address)
    }

    override fun insertAll(addresses: List<Address>) {
        addressDao.insertAll(addresses)
    }

    override fun getAllAddresses(): Flow<List<Address>> {
        return addressDao.getAllAddresses()
    }

    override fun getAddressByPhoneNumber(phoneNumber: String): Flow<Address?> {
        return addressDao.getAddressByPhoneNumber(phoneNumber)
    }

    override fun deleteAll() {
        addressDao.deleteAll()
    }

    override suspend fun setToken(type: Preferences.Key<String>, value: String) {
        preferenceDataStore.edit { settings ->
            settings[type] = value
        }
    }

    override fun getToken(type: Preferences.Key<String>): Flow<String> {
        return preferenceDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[type] ?: ""
            }
    }
}