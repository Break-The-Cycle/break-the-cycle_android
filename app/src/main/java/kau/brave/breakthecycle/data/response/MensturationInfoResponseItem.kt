package kau.brave.breakthecycle.data.response

data class MensturationInfoResponseItem(
    val division: String,
    val endDate: String,
    val id: Int? = null,
    val startDate: String
)