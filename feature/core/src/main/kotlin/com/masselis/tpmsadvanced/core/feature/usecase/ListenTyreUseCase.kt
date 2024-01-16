package com.masselis.tpmsadvanced.core.feature.usecase

import com.masselis.tpmsadvanced.data.vehicle.model.Tyre
import kotlinx.coroutines.flow.Flow

internal interface ListenTyreUseCase {
    fun listen(): Flow<Tyre.Located>
}
