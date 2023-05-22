package com.masselis.tpmsadvanced.data.car.ioc

import com.masselis.tpmsadvanced.data.car.interfaces.SensorDatabase
import com.masselis.tpmsadvanced.data.car.interfaces.TyreDatabase
import com.masselis.tpmsadvanced.data.car.interfaces.VehicleDatabase
import dagger.Component

@DataVehicleComponent.Scope
@Component(
    modules = [Module::class]
)
public interface DataVehicleComponent {

    public val debugComponentFactory: DebugComponent.Factory

    public val vehicleDatabase: VehicleDatabase
    public val sensorDatabase: SensorDatabase
    public val tyreDatabase: TyreDatabase

    @javax.inject.Scope
    public annotation class Scope

    public companion object : DataVehicleComponent by DaggerDataVehicleComponent.create()
}
