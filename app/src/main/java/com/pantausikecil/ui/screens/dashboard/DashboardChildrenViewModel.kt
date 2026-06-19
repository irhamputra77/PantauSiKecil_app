package com.pantausikecil.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pantausikecil.data.repository.ChildrenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardChildrenUiState(
    val isLoading: Boolean = false,
    val children: List<DataAnakUiModel> = emptyList(),
    val errorMessage: String? = null,
    val reportFileName: String? = null
)

class DashboardChildrenViewModel(
    private val repository: ChildrenRepository,
    private val token: String,
    private val posyanduId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardChildrenUiState())
    val uiState: StateFlow<DashboardChildrenUiState> = _uiState.asStateFlow()

    init {
        fetchChildren()
    }

    fun fetchChildren() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                repository.getChildren(token)
            }.onSuccess { children ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    children = children,
                    errorMessage = null
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Gagal mengambil data anak"
                )
            }
        }
    }

    fun generateReport() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                reportFileName = null
            )

            runCatching {
                repository.generateReport(
                    token = token,
                    posyanduId = posyanduId
                )
            }.onSuccess { fileName ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reportFileName = fileName,
                    errorMessage = null
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Gagal mencetak laporan"
                )
            }
        }
    }
}