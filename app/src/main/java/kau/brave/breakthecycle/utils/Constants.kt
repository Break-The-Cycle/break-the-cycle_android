package kau.brave.breakthecycle.utils

import kau.brave.breakthecycle.ui.model.Screen

object Constants {

    val BOTTOM_NAV_ITEMS = listOf<Screen>(Screen.Home, Screen.Calendar, Screen.Mypage)
    val PASSWD_REGEX =
        Regex("""^(?=.*[A-Za-z])(?=.*\d)(?=.*[@${'$'}!%*#?&])[A-Za-z\d@${'$'}!%*#?&]{8,}${'$'}""")

    
    const val SPLASH_ROUTE = "nav-splash"

    const val LOGIN_ROUTE = "nav-login"
    const val SIGNIN_GENDER_ROUTE = "nav-signin-gender"
    const val SIGNIN_PHONE_VERIFY_ROUTE = "nav-signin-phone-verify"
    const val SIGNIN_ID_PASSWD_ROUTE = "nav-signin-id-passwd"
    const val SIGNIN_COMPLETE_ROUTE = "nav-signin-complete"

    // 시크릿 온보딩 라욷트
    const val SECERET_ONBOARD_ENTER_ROUTE = "nav-onboard-secret-enter"
    const val SECERET_ONBOARD_DATE_WRITE_ROUTE = "nav-onboard-date-write"
    const val SECERET_ONBOARD_DATE_INFO_ROUTE = "nav-onboard-date-info"
    const val SECERET_ONBOARD_REPORT_ROUTE = "nav-onboard-report"
    const val SECERET_ONBOARD_MYPAGE_ROUTE = "nav-onboard-mypage"


    const val ONBOARD_ROUTE = "nav-onboard"
    const val DIARY_WRITE_ROUTE = "nav-diary-write"

    const val MAIN_GRAPH = "main_graph"
    const val AUTH_GRAPH = "login_graph"
}