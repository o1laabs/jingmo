package com.hefengbao.jingmo.ui.screen.chinesewisecrack

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hefengbao.jingmo.data.model.AppStatus
import com.hefengbao.jingmo.data.model.ChineseColor
import com.hefengbao.jingmo.data.repository.ChineseColorRepository
import com.hefengbao.jingmo.data.repository.ChineseWisecrackRepository
import com.hefengbao.jingmo.data.repository.PreferenceRepository
import com.hefengbao.jingmo.ui.screen.chinesewisecrack.nav.ChineseWisecrackCaptureArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChineseWisecrackCaptureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chineseWisecrackRepository: ChineseWisecrackRepository,
    private val chineseColorRepository: ChineseColorRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    private val args: ChineseWisecrackCaptureArgs = ChineseWisecrackCaptureArgs(savedStateHandle)

    lateinit var appStatus: AppStatus

    init {
        viewModelScope.launch {
            appStatus = preferenceRepository.getAppStatus().first()
        }
    }

    val chineseWisecrack =
        chineseWisecrackRepository.getChineseCrack(args.chineseWisecrackId.toInt())
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    private val _chineseColors: MutableStateFlow<List<ChineseColor>> = MutableStateFlow(emptyList())
    val chineseColors: SharedFlow<List<ChineseColor>> = _chineseColors
    fun getColors() {
        viewModelScope.launch {
            _chineseColors.value = chineseColorRepository.getList()
        }
    }

    fun setCaptureColor(color: String) {
        viewModelScope.launch {
            preferenceRepository.setCaptureTextColor(color)
        }
    }

    fun setCaptureBackgroundColor(color: String) {
        viewModelScope.launch {
            preferenceRepository.setCaptureBackgroundColor(color)
        }
    }
}