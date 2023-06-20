package kau.brave.breakthecycle.domain.model

data class BraveDiary(
    val title: String = "",
    val contents: String = "",
    val date: String = "",
    val images: MutableList<String> = mutableListOf<String>(), // 바이트코드
    val reportDate: String = "",
) {
    companion object {
        fun default(): BraveDiary {
            return BraveDiary()
        }
    }
}