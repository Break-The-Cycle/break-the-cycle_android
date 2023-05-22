package kau.brave.breakthecycle.data.request

data class OnBoardRequest(
    val endDate: String,
    val period: Int,
    val startDate: String
)