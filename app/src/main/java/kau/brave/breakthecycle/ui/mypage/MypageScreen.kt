package kau.brave.breakthecycle.ui.mypage

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kau.brave.breakthecycle.contentprovider.CameraFileProvider
import kau.brave.breakthecycle.domain.model.ApplicationState
import kau.brave.breakthecycle.utils.Constants

@Composable
fun MypageScreen(appState: ApplicationState) {

    val context = LocalContext.current

    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val navigateToReviewWrite: (Uri?) -> Unit = { uri ->
        if (uri != null) {
            appState.navController.currentBackStackEntry?.arguments?.putParcelable(
                "uri",
                uri
            )
            appState.navigate(Constants.DIARY_WRITE_ROUTE)
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

    Column(
        modifier = Modifier.fillMaxSize(),
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