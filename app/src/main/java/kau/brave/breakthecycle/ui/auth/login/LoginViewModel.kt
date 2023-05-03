package kau.brave.breakthecycle.ui.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private val _id = mutableStateOf("")
    val id: State<String> get() = _id

    private val _passwd = mutableStateOf("")
    val passwd: State<String> get() = _passwd

    fun updateId(id: String) {
        _id.value = id
    }

    fun updatePasswd(passwd: String) {
        _passwd.value = passwd
    }

}