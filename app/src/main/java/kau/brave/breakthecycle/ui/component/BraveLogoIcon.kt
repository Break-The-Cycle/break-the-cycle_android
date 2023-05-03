package kau.brave.breakthecycle.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.RoseDaysApplication.Companion.isSecretMode
import kau.brave.breakthecycle.utils.noRippleClickable
import kotlinx.coroutines.delay

@Composable
fun ColumnScope.BraveLogoIcon(onClick: () -> Unit) {

    var count by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = count) {
        if (count >= 5) {
            count = 0
            onClick()
            isSecretMode.value = !isSecretMode.value
        }
        delay(2000L)
        Log.i("dlgocks1", "초기화됌")
        count = 0
    }

    Image(
        painter = painterResource(id = if (isSecretMode.value) R.mipmap.img_secret_logo_small else R.drawable.img_logo_small),
        contentDescription = "IMG_LOGO_SMALL",
        modifier = Modifier
            .size(56.dp)
            .align(Alignment.CenterHorizontally)
            .noRippleClickable {
                count++
            }
    )
}