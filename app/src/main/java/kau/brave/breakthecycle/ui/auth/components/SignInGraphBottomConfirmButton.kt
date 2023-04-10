package kau.brave.breakthecycle.ui.auth.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.brave.breakthecycle.ui.theme.Disabled
import kau.brave.breakthecycle.ui.theme.Main
import kau.brave.breakthecycle.ui.theme.White

@Composable
fun ColumnScope.SignInGraphBottomConfirmButton(
    onClick: () -> Unit,
    enabled: Boolean,
) {
    Spacer(modifier = Modifier.weight(1f))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 90.dp),
        shape = RoundedCornerShape(5.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = if (enabled) Main else Disabled),
        onClick = onClick
    ) {
        Text(
            text = "완료", color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 3.dp)
        )
    }
}