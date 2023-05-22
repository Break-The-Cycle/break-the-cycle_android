package kau.brave.breakthecycle.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import kau.brave.breakthecycle.domain.model.BraveDate
import kau.brave.breakthecycle.ui.model.Screen

object Constants {

    const val DEV_URL = "http://dev-break-the-cycle.ap-northeast-2.elasticbeanstalk.com/api/"
    const val BRAVE_DATASTORE = "BRAVE_DATASTORE"
    val PREF_ACCESS_TOKEN = stringPreferencesKey("access_token")
    val PREF_REFRESH_TOKEN = stringPreferencesKey("refresh_token")

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
    const val SECERET_ONBOARD_ROUTE = "nav-secret-onboard"
    const val ONBOARD_ROUTE = "nav-onboard"

    const val DIARY_WRITE_PHOTO_ROUTE = "nav-diary-photo-write"
    const val DIARY_WRITE_ROUTE = "nav-diary-write"

    // Garphs
    const val MAIN_GRAPH = "main_graph"
    const val AUTH_GRAPH = "login_graph"
    const val SIGNIN_GRAPH = "signin_graph"
    const val USERINFO_GRAPH = "userinfo_graph"

    //    const val ONBOARD_GRAPH = "onboard_graph"
//    const val SECERT_ONBOARD_GRAPH = "secret-onboard_graph"
    const val DIARY_WRITE_GRAPH = "diary-write_graph"

    // 생리일 샘플
    val tempMenDays = listOf<BraveDate>(
        BraveDate(2023, 5, 10),
        BraveDate(2023, 5, 11),
        BraveDate(2023, 5, 12),
        BraveDate(2023, 5, 13),
        BraveDate(2023, 5, 14),
    )

    // 배란일 샘플
    val tempBenDays = listOf<BraveDate>(
        BraveDate(2023, 5, 20),
        BraveDate(2023, 5, 21),
        BraveDate(2023, 5, 22),
        BraveDate(2023, 5, 23),
        BraveDate(2023, 5, 24),
    )
}