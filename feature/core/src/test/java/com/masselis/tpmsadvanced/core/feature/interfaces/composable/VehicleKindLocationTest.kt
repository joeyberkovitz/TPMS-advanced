package com.masselis.tpmsadvanced.core.feature.interfaces.composable

import com.masselis.tpmsadvanced.data.car.model.Vehicle.Kind.Location.Axle
import com.masselis.tpmsadvanced.data.car.model.Vehicle.Kind.Location.Side
import com.masselis.tpmsadvanced.data.car.model.Vehicle.Kind.Location.Wheel
import com.masselis.tpmsadvanced.data.record.model.SensorLocation.Axle.FRONT
import com.masselis.tpmsadvanced.data.record.model.SensorLocation.FRONT_LEFT
import com.masselis.tpmsadvanced.data.record.model.SensorLocation.Side.LEFT
import org.junit.Test
import kotlin.test.assertEquals

internal class VehicleKindLocationTest {

    /**
     * I need `Location` to be explicit when `toString()` is called to ensure that the keys used
     * by the method `viewModel(key="")` are valid.
     */
    @Test
    fun toStringTest() {
        assertEquals("Axle(axle=FRONT)", "${Axle(FRONT)}")
        assertEquals("Side(side=LEFT)", "${Side(LEFT)}")
        assertEquals("Wheel(location=FRONT_LEFT)", "${Wheel(FRONT_LEFT)}")
    }
}