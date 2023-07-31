package com.masselis.tpmsadvanced.core.feature.usecase

import com.masselis.tpmsadvanced.data.vehicle.interfaces.SensorDatabase
import com.masselis.tpmsadvanced.data.vehicle.model.Vehicle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Named

internal class ClearBoundSensorsUseCase @Inject constructor(
    @Named("base") private val vehicle: Vehicle,
    private val sensorDatabase: SensorDatabase,
) {

    suspend fun clear() = sensorDatabase.deleteFromVehicle(vehicle.uuid)

    fun isClearingAllowed() = sensorDatabase.countByVehicle(vehicle.uuid)
        .map { it > 0 }
        .distinctUntilChanged()

}