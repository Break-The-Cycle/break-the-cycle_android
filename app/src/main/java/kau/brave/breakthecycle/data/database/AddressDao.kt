package kau.brave.breakthecycle.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kau.brave.breakthecycle.domain.model.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: Address)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(addresses: List<Address>)

    @Query("SELECT * FROM address")
    fun getAllAddresses(): Flow<List<Address>>

    @Query("SELECT * FROM address WHERE phoneNumber = :phoneNumber")
    fun getAddressByPhoneNumber(phoneNumber: String): Flow<Address?>

    @Query("DELETE FROM address")
    fun deleteAll()

}