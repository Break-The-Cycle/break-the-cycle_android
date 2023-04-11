package kau.brave.breakthecycle.utils

import kau.brave.breakthecycle.ui.model.Screen

object Constants {

    val BOTTOM_NAV_ITEMS = listOf<Screen>(Screen.Home, Screen.Calendar, Screen.Mypage)
    val PASSWD_REGEX =
        Regex("""^(?=.*[A-Za-z])(?=.*\d)(?=.*[@${'$'}!%*#?&])[A-Za-z\d@${'$'}!%*#?&]{8,}${'$'}""")


    const val SPLASH_ROUTE = "nav-splash"

    const val LOGIN_ROUTE = "nav-login"

    // Signin Graph
    const val SIGNIN_PHONE_VERIFY_ROUTE = "nav-signin-phone-verify"
    const val SIGNIN_ID_PASSWD_ROUTE = "nav-signin-id-passwd"

    // Userinfo Graph
    const val SIGNIN_SETTING_ROUTE = "nav-signin-setting"
    const val SIGNIN_COMPLETE_ROUTE = "nav-signin-complete"
    const val USERINFO_MENSTRUATION_DURATION_ROUTE = "nav-userinfo-menstruation-duration"
    const val USERINFO_MENSTRUATION_DATE_ROUTE = "nav-userinfo-menstruation-date"

    // Onboard Graph
    const val SECERET_ONBOARD_ENTER_ROUTE = "nav-onboard-secret-enter"
    const val SECERET_ONBOARD_DATE_WRITE_ROUTE = "nav-onboard-date-write"
    const val SECERET_ONBOARD_DATE_INFO_ROUTE = "nav-onboard-date-info"
    const val SECERET_ONBOARD_REPORT_ROUTE = "nav-onboard-report"
    const val SECERET_ONBOARD_MYPAGE_ROUTE = "nav-onboard-mypage"
    const val ONBOARD_CYCLE_ROUTE = "nav-onboard-cycle"
    const val ONBOARD_DATE_WRITE_ROUTE = "nav-onboard-date-write"

    const val DIARY_WRITE_ROUTE = "nav-diary-write"

    // Garphs
    const val MAIN_GRAPH = "main_graph"
    const val AUTH_GRAPH = "login_graph"
    const val SIGNIN_GRAPH = "signin_graph"
    const val USERINFO_GRAPH = "userinfo_graph"
    const val ONBOARD_GRAPH = "onboard_graph"
}