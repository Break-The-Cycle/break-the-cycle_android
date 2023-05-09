@file:OptIn(ExperimentalMaterialApi::class)

package kau.brave.breakthecycle.ui.diary

import android.icu.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.contentprovider.CameraFileProvider
import kau.brave.breakthecycle.theme.*
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.utils.rememberApplicationState
import kotlinx.coroutines.launch

@Preview
@Composable
fun DiaryWriteScreen(appState: ApplicationState = rememberApplicationState()) {

    val context = LocalContext.current
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

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

    var addedImage by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val navigateToReviewWrite: (Uri?) -> Unit = { uri ->
        if (uri != null) {
            // TODO 손글씨는 나중에 넣는걸로..
//            appState.navController.currentBackStackEntry?.arguments?.putParcelable("uri", uri)
//            appState.navigate(DIARY_WRITE_PHOTO_ROUTE)
            addedImage = addedImage + uri
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

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetElevation = 10.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "갤러리 사진 선택",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            galleryLauncher.launch("image/*")
                            scope.launch {
                                bottomState.hide()
                            }
                        }
                        .padding(vertical = 15.dp),
                    color = IOSBlue,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                )
                Divider(color = Gray100)
                Text(
                    text = "카메라로 촬영",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val uri = CameraFileProvider.getImageUri(context)
                            imageUri = uri
                            camearLauncher.launch(uri)
                            scope.launch {
                                bottomState.hide()
                            }
                        }
                        .padding(vertical = 15.dp),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = IOSBlue
                )
                Divider(color = Gray100)
                Text(
                    text = "취소",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                bottomState.hide()
                            }
                        }
                        .padding(vertical = 15.dp),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    color = IOSRed
                )

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
                Box(
                    modifier = Modifier.fillMaxWidth(),
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
                        text = "일기 작성하기",
                        fontSize = 16.sp,
                        color = Gray800,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "완료",
                        fontSize = 16.sp,
                        color = Gray800,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                appState.popBackStack()
                            }
                            .padding(10.dp)
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val calendar = Calendar.getInstance()
                    val now =
                        "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월 ${
                            calendar.get(
                                Calendar.DAY_OF_MONTH
                            )
                        }일"
                    Text(
                        text = "$now", fontSize = 14.sp,
                        color = Gray300,
                    )
                    Text(
                        text = "${content.length} / 1,000 ", fontSize = 14.sp,
                        color = Gray300,
                    )
                }


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
                    .padding(bottom = 50.dp)
                    .height(200.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "사진을 등록해주세요.(${addedImage.size}/5)",
                    fontSize = 18.sp,
                    color = Gray800
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    addedImage.forEach {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(it)
                                    .build()
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .size(106.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Gray300)
                        )
                    }
                    if (addedImage.size < 5) {
                        Box(
                            modifier = Modifier
                                .size(106.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Gray300)
                                .clickable {
                                    scope.launch {
                                        bottomState.show()
                                    }
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
}

