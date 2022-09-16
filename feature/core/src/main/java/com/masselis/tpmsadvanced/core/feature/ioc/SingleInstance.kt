package com.masselis.tpmsadvanced.core.feature.ioc

import javax.inject.Scope

/** Used instantiate a binding only once into the current component */
@Scope
internal annotation class SingleInstance