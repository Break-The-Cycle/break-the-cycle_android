package kau.brave.breakthecycle.ui.mypage.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.database.AddressRepository
import kau.brave.breakthecycle.di.DispatcherModule
import kau.brave.breakthecycle.domain.model.Address
import kau.brave.breakthecycle.utils.Constants.PREF_MESSAGE_TEXT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AddressScreenUiState(
    val addresses: List<Address> = listOf(Address.DEFAULT_ADDRESS),
    val messageText: String = ""
)

@HiltViewModel
class ReportAddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _addresses = MutableStateFlow(listOf<Address>())
    private val _messageText = MutableStateFlow("")

    init {
        getAllAddresses()
        getMessageText()
    }

    val uiState = combine(_addresses, _messageText) { addresses, messageText ->
        AddressScreenUiState(addresses, messageText)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AddressScreenUiState())

    fun insertAddress(address: Address) = viewModelScope.launch(ioDispatcher) {
        addressRepository.insert(address)
    }

    private fun getAllAddresses() = viewModelScope.launch {
        addressRepository.getAllAddresses().collectLatest {
            _addresses.value = it.sortedBy { it.name }
        }
    }

    fun setMessageText() = viewModelScope.launch {
        addressRepository.setToken(PREF_MESSAGE_TEXT, _messageText.value)
    }

    private fun getMessageText() = viewModelScope.launch {
        addressRepository.getToken(PREF_MESSAGE_TEXT).collectLatest {
            _messageText.value = it
        }
    }

    fun updateMessages(text: String) = viewModelScope.launch {
        _messageText.value = text
    }

}