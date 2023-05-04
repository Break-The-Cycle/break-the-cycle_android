package kau.brave.breakthecycle.ui.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kau.brave.breakthecycle.R

@Composable
fun BackIcon(onbackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        IconButton(
            onClick = onbackPressed,
            modifier = Modifier.size(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "IC_ARROW_BACK"
            )
        }
    }
}