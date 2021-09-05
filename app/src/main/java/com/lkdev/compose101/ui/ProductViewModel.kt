package com.lkdev.compose101.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkdev.compose101.data.repository.Repository
import com.lkdev.compose101.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state

    init {
        viewModelScope.launch {
            repository.getProducts()
                .onStart {
                    _state.emit(state.value.copy(loading = true))
                }
                .catch {
                    _state.emit(state.value.copy(loading = false, errorMessage = it.message))
                }
                .collect {
                    _state.emit(state.value.copy(loading = false, products = it))
                }
        }
    }

    data class ViewState(
        val loading: Boolean = false,
        val products: List<Product> = emptyList(),
        val errorMessage: String? = null
    )
}