package com.sberkozd.productapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.productapp.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel() {

    private val _productListResponse: MutableStateFlow<List<Product>> = MutableStateFlow(
        mutableListOf()
    )
    val productListResponse: StateFlow<List<Product>> = _productListResponse

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductList(
                onError = { error ->
                    error?.let {
                        eventChannel.send(Event.ShowError(it))
                    }
                }
            )
                .stateIn(viewModelScope)
                .collect {
                    if (it != null) {
                        _productListResponse.value = it
                    }
                }
        }
    }
}

sealed class Event {
    data class ShowError(val message: String) : Event()
}