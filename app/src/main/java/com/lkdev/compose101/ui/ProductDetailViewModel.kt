package com.lkdev.compose101.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkdev.compose101.data.repository.Repository
import com.lkdev.compose101.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state

    init {
        val id = savedStateHandle.get<Int>("productId")

        viewModelScope.launch {
            id?.let {
                repository.getProduct(id)
                    .onStart {
                        _state.emit(state.value.copy(loading = true))
                    }
                    .catch {
                        _state.emit(state.value.copy(loading = false, errorMessage = it.message))
                    }
                    .collect {
                        _state.emit(state.value.copy(loading = false, product = it))
                    }
            } ?: run {
                _state.emit(state.value.copy(errorMessage = "ไม่พบข้อมูล"))
            }
        }
    }

    data class ViewState(
        val loading: Boolean = false,
        val product: Product? = null,
        val errorMessage: String? = null
    )
}