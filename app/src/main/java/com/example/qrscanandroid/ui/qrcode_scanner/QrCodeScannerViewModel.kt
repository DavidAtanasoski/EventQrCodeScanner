package com.example.qrscanandroid.ui.qrcode_scanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrscanandroid.domain.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrCodeScannerViewModel @Inject constructor(
    private val qrCodeRepository: QrCodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QrCodeScannerUiState())

    val uiState: StateFlow<QrCodeScannerUiState> = _uiState
    fun onQrCodeScanned(scannedId: String) {
        Log.d("VIEWMODEL:", "QR Scanned!")
        viewModelScope.launch {
            setStatusLoading(true)

            val validationResult = qrCodeRepository.doValidation(scannedId)
            Log.d("ValRes:", "$validationResult:")

            if (validationResult) {
                setStatusLoading(false)
                setStatusSuccess(true)
            } else {
                setStatusLoading(false)
                setStatusSuccess(false)
                setStatusFailed(true)
            }
        }
    }

    fun resetState() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isFailed = false,
                isSuccess = false,
            )
        }
    }

    private fun setStatusLoading(status: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = status,
            )
        }
    }

    private fun setStatusSuccess(status: Boolean) {
        _uiState.update {
            it.copy(
                isSuccess = status,
            )
        }
    }

    private fun setStatusFailed(status: Boolean) {
        _uiState.update {
            it.copy(
                isFailed = status,
            )
        }
    }
}