package kau.brave.breakthecycle.data.request

data class ExportViolentRequest(
    val password: String,
    val fromDate: String,
    val toDate: String
)