/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.echarts.aes

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.ggdsl.dsl.internal.BindingContext
import org.jetbrains.kotlinx.ggdsl.echarts.scale.EchartsNonPositionalMappingParameters
import org.jetbrains.kotlinx.ggdsl.echarts.scale.nonPosMapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.NonPositionalMapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.NonPositionalMappingParameters
import org.jetbrains.kotlinx.ggdsl.util.color.Color

public interface WithColor : BindingContext {
    public var color: Color?
        get() = null
        set(value) {
            addNonPositionalSetting(COLOR, value)
        }

    public fun <T> color(
        column: ColumnReference<T>, params: EchartsNonPositionalMappingParameters<T, Color>.() -> Unit = {}
    ): NonPositionalMapping<T, Color> = nonPosMapping(COLOR, column, params)

    public fun <T> color(
        values: Iterable<T>, name: String? = null, params: NonPositionalMappingParameters<T, Color>.() -> Unit = {}
    ): NonPositionalMapping<T, Color> = nonPosMapping(COLOR, values, name, params)

    public fun color(
        column: String, params: EchartsNonPositionalMappingParameters<*, Color>.() -> Unit = {}
    ): NonPositionalMapping<*, Color> = nonPosMapping(COLOR, column, params)

    public fun <T> color(
        values: DataColumn<T>, params: EchartsNonPositionalMappingParameters<T, Color>.() -> Unit = {}
    ): NonPositionalMapping<T, Color> = nonPosMapping(COLOR, values, params)
}