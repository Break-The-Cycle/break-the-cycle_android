package kau.brave.breakthecycle.ui.mypage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.theme.Gray800
import kau.brave.breakthecycle.theme.Main
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kotlinx.coroutines.launch


@Composable
@Preview
fun DataExportScreen(appState: ApplicationState = rememberApplicationState()) {

    val viewModel: MypageViewModel = hiltViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.exportDiaryToken()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {

            IconButton(
                onClick = {
                    appState.navController.popBackStack()
                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "IC_ARROW_BACK"
                )
            }

            Text(
                text = "데이터 내보내기",
                fontSize = 16.sp,
                color = Gray800,
                modifier = Modifier.padding(start = 10.dp),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFEEEEEE))
                .wrapContentWidth()
                .padding(20.dp)
        ) {
            Text(text = viewModel.exportToken.value, modifier = Modifier.align(Alignment.Center))
        }

        Button(
            onClick = {
                val clipboard: ClipboardManager =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("exportToken", viewModel.exportToken.value)
                clipboard.setPrimaryClip(clip)
                scope.launch {
                    appState.showSnackbar("토큰이 복사되었습니다.")
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Main)
        ) {
            Text(
                text = "복사하기",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

    }
}