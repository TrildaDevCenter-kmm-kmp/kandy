/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.letsplot.layers.context.aes

import org.jetbrains.kotlinx.kandy.dsl.internal.BindingCollector
import org.jetbrains.kotlinx.kandy.ir.aes.AesName
import org.jetbrains.kotlinx.kandy.ir.bindings.PositionalSetting

public class ConstantSetter(private val aesName: AesName, private val bindingCollector: BindingCollector) {
    public fun<T> constant(value: T){
        bindingCollector.settings[aesName] = PositionalSetting<T>(aesName, value)
    }
}
