package kau.brave.breakthecycle.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kau.brave.breakthecycle.domain.model.Address
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(entities = [Address::class], version = 1)
abstract class BraveDatabase : RoomDatabase() {

    abstract fun addressDao(): AddressDao

    companion object {
        fun getInstance(context: Context): BraveDatabase = Room
            .databaseBuilder(context, BraveDatabase::class.java, "BRAVE_DB")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    Executors.newSingleThreadExecutor().execute {
                        runBlocking {
                            getInstance(context).addressDao().insert(Address.DEFAULT_ADDRESS)
                        }
                    }
                }
            })
            .build()
    }
}