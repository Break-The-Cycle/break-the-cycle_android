@file:OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)

package kau.brave.breakthecycle.ui.diary

import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.Glide.init
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.ui.diary.viewmodel.DiaryDetailViewModel
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Gray600
import kau.brave.breakthecycle.theme.Gray800
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState

@Preview
@Composable
fun DiaryDetailScreen(
    appState: ApplicationState = rememberApplicationState(),
    targetDate: String = "",
) {

    val viewModel: DiaryDetailViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getDiaryDetail(targetDate)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {

            IconButton(
                onClick = {
                    appState.navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "IC_ARROW_BACK"
                )
            }

            Text(
                text = "일기 상세 조회",
                fontSize = 16.sp,
                color = Gray800,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = uiState.title,
                fontSize = 18.sp,
                color = Gray800,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = uiState.reportDate,
                fontSize = 14.sp,
                color = Gray800,
                fontWeight = FontWeight.Bold
            )
        }

        if (uiState.photoUris.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            ) {
                val imageByteArrays: List<ByteArray> = uiState.photoUris.map {
                    Base64.decode(it, Base64.DEFAULT)
                }
                HorizontalPager(
                    count = imageByteArrays.size,
                    modifier = Modifier
                        .fillMaxSize(),
                    state = pagerState
                ) { page ->
                    GlideImage(
                        model = imageByteArrays[page],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(10.dp),
                    activeColor = Color.White,
                    inactiveColor = Gray300
                )


            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = uiState.contents, fontSize = 16.sp, color = Gray600)
        }
    }
}