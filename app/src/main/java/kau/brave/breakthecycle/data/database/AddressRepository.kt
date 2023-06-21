package kau.brave.breakthecycle.data.database

import androidx.datastore.preferences.core.Preferences
import kau.brave.breakthecycle.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepository {

    fun insert(address: Address)

    fun insertAll(addresses: List<Address>)

    fun getAllAddresses(): Flow<List<Address>>

    fun getAddressByPhoneNumber(phoneNumber: String): Flow<Address?>

    fun deleteAll()

    suspend fun setToken(type: Preferences.Key<String>, value: String)

    fun getToken(type: Preferences.Key<String>): Flow<String>
}