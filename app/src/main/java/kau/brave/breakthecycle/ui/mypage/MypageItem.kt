package kau.brave.breakthecycle.ui.mypage

data class MypageItem(
    val title: String,
    val onClick: () -> Unit = {},
    val istoggle: Boolean = false,
    val onToggleClick: (Boolean) -> Unit = {},
)
