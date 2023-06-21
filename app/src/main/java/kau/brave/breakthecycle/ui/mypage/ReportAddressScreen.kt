@file:OptIn(ExperimentalPermissionsApi::class)

package kau.brave.breakthecycle.ui.mypage

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kau.brave.breakthecycle.R
import kau.brave.breakthecycle.domain.model.Address
import kau.brave.breakthecycle.theme.*
import kau.brave.breakthecycle.ui.component.HeightSpacer
import kau.brave.breakthecycle.ui.model.ApplicationState
import kau.brave.breakthecycle.ui.mypage.viewmodel.ReportAddressViewModel
import kau.brave.breakthecycle.utils.rememberApplicationState
import kotlinx.coroutines.launch


@Composable
@Preview
fun ReportAddressScreen(appState: ApplicationState = rememberApplicationState()) {

    val context = LocalContext.current

    val viewModel: ReportAddressViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val radioOptions = listOf("문자", "전화")
    val (selectedItem, onItemSelected) = remember { mutableStateOf(radioOptions[0]) }

    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    val contractReader = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { uri ->
            val uri = uri ?: return@rememberLauncherForActivityResult
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameColumnIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val name = it.getString(nameColumnIndex)

                    val idColumnIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                    val contactId = it.getString(idColumnIndex)

                    val phoneCursor = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null
                    )
                    phoneCursor?.use { phoneCursor ->
                        while (phoneCursor.moveToNext()) {
                            val phoneNumberColumnIndex =
                                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            val phoneNumber = phoneCursor.getString(phoneNumberColumnIndex)
                            viewModel.insertAddress(
                                Address(
                                    name = name,
                                    phoneNumber = phoneNumber.replace("-", "")
                                )
                            )
                        }
                    }
                }
            }
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        /** 마이 페이지 탑 바 */
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {

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
                    text = "신고 범위 설정",
                    fontSize = 16.sp,
                    color = Gray800,
                    modifier = Modifier.padding(start = 10.dp),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "완료", fontSize = 16.sp, color = Main, fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    viewModel.setMessageText()
                    appState.navController.popBackStack()
                }
            )
        }

        HeightSpacer(dp = 20.dp)
        /** 신고 방식 */
        Column {
            Text(
                text = "신고 방식",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp),
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                radioOptions.forEach { label ->
                    Row(
                        modifier = Modifier
                            .height(56.dp)
                            .selectable(
                                selected = (selectedItem == label),
                                onClick = { onItemSelected(label) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            modifier = Modifier.padding(end = 5.dp),
                            selected = (selectedItem == label),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Main,
                                unselectedColor = Gray200
                            ),
                            onClick = null
                        )
                        Text(text = label, fontSize = 14.sp)
                    }
                }
            }
        }

        HeightSpacer(dp = 40.dp)
        /** 신고 대상 */
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "신고 대상",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "내 연락처에서 추가하기", fontSize = 14.sp, color = White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Main)
                        .clickable {
                            // 주소록 가져오기
                            try {
                                contractReader.launch(null)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
            HeightSpacer(dp = 20.dp)
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                uiState.addresses.forEach {
                    AddressItem(
                        address = it,
                        updateAddress = viewModel::insertAddress
                    )
                }
            }

        }

        HeightSpacer(dp = 40.dp)
        /** 신고 커스텀 메시지 설정 */
        Column {
            Text(
                text = "신고 메시지 설정",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(text = "위급할 때 보낼 메시지 내용을 적어주세요. (주소, 위치 등)", fontSize = 15.sp, color = Gray700)

            Text(
                text = "${uiState.messageText.length}/500",
                fontSize = 12.sp,
                color = Gray300,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp)
            )

            BasicTextField(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(Gray50),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = Color.Black,
                ),
                value = uiState.messageText,
                onValueChange = {
                    if (uiState.messageText.length < 500) {
                        viewModel.updateMessages(it)
                    }
                },
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        if (uiState.messageText.isEmpty()) {
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
    }
}

@Composable
fun AddressItem(address: Address, updateAddress: (Address) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                updateAddress(
                    address.copy(
                        isSelected = !address.isSelected
                    )
                )
            }
    ) {
        Checkbox(
            checked = address.isSelected,
            onCheckedChange = {
                updateAddress(
                    address.copy(
                        isSelected = !address.isSelected
                    )
                )
            },
            colors = CheckboxDefaults.colors(Main)
        )
        Text(
            text = address.name,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
