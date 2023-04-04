package kau.brave.breakthecycle.ui.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import kau.brave.breakthecycle.ui.auth.login.LoginIdPasswdScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginIdPasswdScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            LoginIdPasswdScreen()
        }
    }

    @Test
    fun `아이디가_7글자_이하이면_로그인버튼이_비활성화된다`() {
        composeTestRule.onNodeWithTag(testTag = "ID_TEXT_FIELD")
            .performTextInput(NOT_VERIFIED_ID)
        composeTestRule.onNodeWithTag(testTag = "PASSWD_TEXT_FIELD")
            .performTextInput("123456123a@s")
        composeTestRule.onNodeWithText("로그인").assertIsNotEnabled()
    }

    @Test
    fun `비밀번호에_특수문자가_없으면_로그인버튼이_비활성화된다`() {
        composeTestRule.onNodeWithTag(testTag = "ID_TEXT_FIELD")
            .performTextInput(VERIFIED_ID)
        composeTestRule.onNodeWithTag(testTag = "PASSWD_TEXT_FIELD")
            .performTextInput(NOT_CONTAINS_SPECIAL_CHARACTER_PASSWD)
        composeTestRule.onNodeWithText("로그인").assertIsNotEnabled()
    }

    @Test
    fun `비밀번호에_문자가_없으면_로그인버튼이_비활성화된다`() {
        composeTestRule.onNodeWithTag(testTag = "ID_TEXT_FIELD")
            .performTextInput(VERIFIED_ID)
        composeTestRule.onNodeWithTag(testTag = "PASSWD_TEXT_FIELD")
            .performTextInput(NOT_CONTAINS_CHARACTER_PASSWD)
        composeTestRule.onNodeWithText("로그인").assertIsNotEnabled()
    }

    @Test
    fun `비밀번호에_숫자가_없으면_로그인버튼이_비활성화된다`() {
        composeTestRule.onNodeWithTag(testTag = "ID_TEXT_FIELD")
            .performTextInput(VERIFIED_ID)
        composeTestRule.onNodeWithTag(testTag = "PASSWD_TEXT_FIELD")
            .performTextInput(NOT_CONTAINS_NUMBER_PASSWD)
        composeTestRule.onNodeWithText("로그인").assertIsNotEnabled()
    }

    @Test
    fun `비밀번호가_8자_미만이면_로그인버튼이_비활성화된다`() {
        composeTestRule.onNodeWithTag(testTag = "ID_TEXT_FIELD")
            .performTextInput(VERIFIED_ID)
        composeTestRule.onNodeWithTag(testTag = "PASSWD_TEXT_FIELD")
            .performTextInput(WITHIN_8_CHARACTERS_PASSWD)
        composeTestRule.onNodeWithText("로그인").assertIsNotEnabled()
    }

    @Test
    fun `아이디와_비밀번호가_모두_형식에_맞다면_로그인버튼이_활성화된다`() {
        composeTestRule.onNodeWithTag(testTag = "ID_TEXT_FIELD")
            .performTextInput(VERIFIED_ID)
        composeTestRule.onNodeWithTag(testTag = "PASSWD_TEXT_FIELD")
            .performTextInput(VERIFIED_PASSWD)
        composeTestRule.onNodeWithText("로그인").assertIsEnabled()
    }

    companion object {
        const val VERIFIED_ID = "Test1234"
        const val VERIFIED_PASSWD = "123123asd@#"
        const val NOT_VERIFIED_ID = "T123"
        const val NOT_CONTAINS_NUMBER_PASSWD = "asdasdadasd@@@@#"
        const val NOT_CONTAINS_CHARACTER_PASSWD = "123456123@@@#"
        const val NOT_CONTAINS_SPECIAL_CHARACTER_PASSWD = "123456123asdasd"
        const val WITHIN_8_CHARACTERS_PASSWD = "1234a@b"
    }

}