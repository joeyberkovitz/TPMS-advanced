package com.masselis.tpmsadvanced.core.feature.interfaces.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masselis.tpmsadvanced.core.ui.asMutableStateFlow
import com.masselis.tpmsadvanced.data.record.interfaces.BluetoothLeScanner
import com.masselis.tpmsadvanced.data.record.interfaces.BluetoothLeScanner.Companion.isChipTurnedOn
import com.masselis.tpmsadvanced.data.record.interfaces.BluetoothLeScanner.Companion.missingPermission
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalCoroutinesApi::class)
internal class PreconditionsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun build(savedStateHandle: SavedStateHandle): PreconditionsViewModel
    }

    sealed class State : Parcelable {
        @Parcelize
        object Loading : State()

        @Parcelize
        object Ready : State()

        @Parcelize
        data class MissingPermission(val permissions: List<String>) : State()

        @Parcelize
        object BluetoothChipTurnedOff : State()
    }

    private val mutableStateFlow = savedStateHandle
        .getLiveData<State>("STATE", State.Loading)
        .asMutableStateFlow()
    val stateFlow = mutableStateFlow.asStateFlow()
    private val trigger = MutableSharedFlow<Unit>(1).also { it.tryEmit(Unit) }

    init {
        trigger
            .flatMapLatest {
                combine(
                    isChipTurnedOn(),
                    flowOf(missingPermission())
                ) { isOn, permissions ->
                    when {
                        permissions.isNotEmpty() -> State.MissingPermission(permissions)
                        isOn.not() -> State.BluetoothChipTurnedOff
                        else -> State.Ready
                    }
                }
            }
            .onEach { mutableStateFlow.value = it }
            .launchIn(viewModelScope)
    }

    fun trigger() {
        viewModelScope.launch { trigger.emit(Unit) }
    }
}
