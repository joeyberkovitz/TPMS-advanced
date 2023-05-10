package com.masselis.tpmsadvanced.core.feature.unit.ioc

import com.masselis.tpmsadvanced.core.feature.unit.interfaces.UnitsViewModel
import com.masselis.tpmsadvanced.data.unit.ioc.DataUnitComponent
import dagger.Component
import javax.inject.Inject

@FeatureUnitComponent.Scope
@Component(
    dependencies = [
        DataUnitComponent::class
    ]
)
public interface FeatureUnitComponent {
    @Component.Factory
    public interface Factory {
        public fun build(dataUnitComponent: DataUnitComponent = DataUnitComponent): FeatureUnitComponent
    }

    @javax.inject.Scope
    public annotation class Scope

    public fun inject(injectable: Injectable)

    public companion object : Injectable()

    public abstract class Injectable protected constructor() :
        FeatureUnitComponent by DaggerFeatureUnitComponent
            .factory()
            .build() {

        @Inject
        internal lateinit var unitsViewModel: UnitsViewModel

        init {
            @Suppress("LeakingThis")
            inject(this)
        }
    }

}
