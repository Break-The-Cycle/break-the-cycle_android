package kau.brave.breakthecycle.data.response

data class DiaryDetailResponseItem(
    val diary: Diary,
    val division: String,
    val id: Int,
    val image: String,
    val reportDate: String
)