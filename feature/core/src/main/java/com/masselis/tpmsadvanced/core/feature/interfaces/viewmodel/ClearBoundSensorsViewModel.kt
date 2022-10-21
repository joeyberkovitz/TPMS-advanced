package com.masselis.tpmsadvanced.core.feature.interfaces.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masselis.tpmsadvanced.core.feature.interfaces.viewmodel.ClearBoundSensorsViewModel.State.AlreadyCleared
import com.masselis.tpmsadvanced.core.feature.interfaces.viewmodel.ClearBoundSensorsViewModel.State.ClearingPossible
import com.masselis.tpmsadvanced.core.feature.usecase.ClearBoundSensorsUseCase
import com.masselis.tpmsadvanced.core.ui.asMutableStateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

internal class ClearBoundSensorsViewModel @AssistedInject constructor(
    private val clearBoundSensorsUseCase: ClearBoundSensorsUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun build(savedStateHandle: SavedStateHandle): ClearBoundSensorsViewModel
    }

    sealed class State : Parcelable {
        @Parcelize
        object ClearingPossible : State()

        @Parcelize
        object AlreadyCleared : State()
    }

    private val mutableStateFlow = savedStateHandle
        .getLiveData<State>("STATE", AlreadyCleared)
        .asMutableStateFlow()
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        clearBoundSensorsUseCase.isClearingAllowed()
            .map { isAllowed -> if (isAllowed) ClearingPossible else AlreadyCleared }
            .onEach { mutableStateFlow.value = it }
            .launchIn(viewModelScope)
    }

    fun clear() = viewModelScope.launch {
        clearBoundSensorsUseCase.clear()
    }
}