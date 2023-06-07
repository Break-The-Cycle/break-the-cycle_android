package kau.brave.breakthecycle.domain.model

enum class DiaryContentType(val serverResponse: String) {
    PICTURE("PICTURE"), DIARY("DIARY");

    companion object {
        fun valueOf(serverResponse: String): DiaryContentType {
            return values().find { it.serverResponse == serverResponse }
                ?: throw IllegalArgumentException("다이어리 타입이 잘못되었습니다.")
        }
    }
}