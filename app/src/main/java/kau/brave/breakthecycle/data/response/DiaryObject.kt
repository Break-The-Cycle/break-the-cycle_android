package kau.brave.breakthecycle.data.response

data class DiaryObject(
    val diaryContents: DiaryContents? = null,
    val division: String, // PICTURE, DIARY
    val id: Int,
    val image: String?,
    val reportDate: String
)