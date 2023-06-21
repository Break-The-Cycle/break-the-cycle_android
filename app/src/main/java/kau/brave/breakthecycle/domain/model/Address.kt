package kau.brave.breakthecycle.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    val name: String,
    @PrimaryKey(autoGenerate = false) val phoneNumber: String,
    val isSelected: Boolean = false,
) {
    companion object {
        val DEFAULT_ADDRESS = Address(
            "경찰",
            "112",
            true
        )
    }
}