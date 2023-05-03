package kau.brave.breakthecycle.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants.ONBOARD_GRAPH
import kau.brave.breakthecycle.utils.Constants.SECERET_ONBOARD_ROUTE
import kau.brave.breakthecycle.utils.Constants.USERINFO_GRAPH
import kau.brave.breakthecycle.utils.noRippleClickable

@Composable
fun MypageScreen(appState: ApplicationState) {

    val mypageItem = listOf(
        MypageItem(title = "생리주기 재설정", onClick = {
            appState.navigate(USERINFO_GRAPH)
        }),
        MypageItem(title = "로즈데이즈에 대하여", onClick = {
            appState.navigate(ONBOARD_GRAPH)
        }),
        MypageItem(title = "비밀번호 변경", onClick = {
            // TODO 비밀번호 변경 그래프 이동
        }),
        MypageItem(title = "로그아웃", onClick = {
            // TODO 로그아웃
        }),
        MypageItem(title = "회원탈퇴", onClick = {
            // TODO 회원탈퇴
        }),
    )

    val secretModeItem = listOf(
        MypageItem(title = "시크릿모드의 기능에 대하여", onClick = {
            appState.navigate(SECERET_ONBOARD_ROUTE)
        }),
        MypageItem(title = "흔들어서 신고하기 설정", istoggle = true, onToggleClick = {
            // TODO 흔들어서 신고하기 설정
        }),
        MypageItem(title = "신고 범위 설정", onClick = {
            // TODO 신고 범위 설정
        }),
        MypageItem(title = "데이터 내보내기", onClick = {
            // TODO 데이터 내보내기
        }),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_logo_small),
            contentDescription = "IMG_LOGO_SMALL",
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "설정",
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Bold
            )
            mypageItem.forEach {
                MypageContent(item = it)
            }
            Text(
                text = "시크릿모드 설정",
                fontSize = 22.sp,
                modifier = Modifier.padding(20.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            secretModeItem.forEach {
                MypageContent(item = it)
            }
        }
    }
}

@Composable
fun MypageContent(item: MypageItem) {
    Divider(color = Gray300, thickness = 1.dp)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (item.istoggle) {
            var isToggled by remember {
                mutableStateOf(false)
            }
            Text(
                text = item.title, modifier = Modifier
                    .weight(1f)
                    .noRippleClickable {
                        isToggled = !isToggled
//                        item.onToggleClick(isToggled)
                    }
                    .padding(20.dp)
            )
            BraveSwitch(
                isSelected = isToggled,
                updateSelected = {
                    isToggled = !isToggled
                },
            )
            Spacer(modifier = Modifier.width(20.dp))
        } else {
            Text(text = item.title, modifier = Modifier
                .weight(1f)
                .clickable {
                    item.onClick()
                }
                .padding(20.dp))
        }

    }
}
