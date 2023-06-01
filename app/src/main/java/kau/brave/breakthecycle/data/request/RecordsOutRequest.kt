package kau.brave.breakthecycle.data.request

data class RecordsOutRequest(
    val fromDate: String,
    val loginId: String,
    val password: String,
    val toDate: String
)