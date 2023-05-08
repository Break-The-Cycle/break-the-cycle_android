@file:OptIn(ExperimentalMaterialApi::class)

package kau.brave.breakthecycle.ui.diary

import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.contentprovider.CameraFileProvider
import kau.brave.breakthecycle.theme.Gray300
import kau.brave.breakthecycle.theme.Gray800
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_GRAPH
import kau.brave.breakthecycle.utils.Constants.DIARY_WRITE_ROUTE
import kau.brave.breakthecycle.utils.rememberApplicationState

@Preview
@Composable
fun DiaryWriteScreen(appState: ApplicationState = rememberApplicationState()) {

    val context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    var title by remember {
        mutableStateOf("")
    }

    var content by remember {
        mutableStateOf("")
    }

    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val navigateToReviewWrite: (Uri?) -> Unit = { uri ->
        if (uri != null) {
            appState.navController.currentBackStackEntry?.arguments?.putParcelable("uri", uri)
            appState.navigate(DIARY_WRITE_ROUTE)
        }
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            navigateToReviewWrite(uri)
        }
    val camearLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            hasImage = it
        }

    LaunchedEffect(key1 = hasImage) {
        if (hasImage) {
            navigateToReviewWrite(imageUri)
        }
    }



    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        sheetPeekHeight = 0.dp, // 바텀 네비게이션바
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Calendar Screen")
                Row {
                    Button(onClick = {
                        galleryLauncher.launch("image/*")
                    }) {
                        Text(text = "갤러리 사진 선택")
                    }

                    Button(onClick = {
                        val uri = CameraFileProvider.getImageUri(context)
                        imageUri = uri
                        camearLauncher.launch(uri)
                    }) {
                        Text(text = "카메라 사진 선택")
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(
                            onClick = {
                                appState.navController.popBackStack()
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                                contentDescription = "IC_ARROW_BACK"
                            )
                        }
                        Text(text = "일기 작성하기", fontSize = 16.sp, color = Gray800)
                    }
                    Text(
                        text = "완료",
                        fontSize = 16.sp,
                        color = Gray800,
                        fontWeight = FontWeight.Bold
                    )
                }

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(0.dp, 10.dp)
                        ) {
                            if (title.isEmpty()) {
                                Text(
                                    "제목을 입력해주세요.",
                                    style = LocalTextStyle.current.copy(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        fontSize = 18.sp,
                                    ),
                                )
                            }
                            innerTextField()
                        }
                    })

                val calendar = Calendar.getInstance()
                val now = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월 ${
                    calendar.get(
                        Calendar.DAY_OF_MONTH
                    )
                }일"
                Text(
                    text = "$now | ${content.length} / 1,000 ", fontSize = 14.sp,
                    color = Gray300, modifier = Modifier.padding(top = 5.dp)
                )
                Divider(color = Gray300, modifier = Modifier.padding(0.dp, 10.dp))

                BasicTextField(modifier = Modifier
                    .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                    ),
                    value = content,
                    onValueChange = {
                        if (it.length < 1_000) {
                            content = it
                        }
                    },
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(0.dp, 10.dp)
                        ) {
                            if (content.isEmpty()) {
                                Text(
                                    "내용을 입력해주세요.",
                                    style = LocalTextStyle.current.copy(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        fontSize = 16.sp,
                                    ),
                                )
                            }
                            innerTextField()
                        }
                    })
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "사진을 입력해주세요.(1/5)", fontSize = 18.sp, color = Gray800)
                Row {
                    Box(
                        modifier = Modifier
                            .size(106.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Gray300)
                            .clickable {

                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                            tint = Color.White
                        )
                    }
                }
            }


        }

    }


}