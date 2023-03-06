package org.jetbrains.kotlinx.ggdsl.letsplot

import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.ggdsl.dsl.internal.PlotContext
import org.jetbrains.kotlinx.ggdsl.ir.bindings.PositionalMapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.PositionalSetting
import org.jetbrains.kotlinx.ggdsl.letsplot.internal.LetsPlotPositionalMappingParameters
import org.jetbrains.kotlinx.ggdsl.letsplot.internal.X
import org.jetbrains.kotlinx.ggdsl.letsplot.internal.Y

public fun <T> PlotContext.x(value: T): PositionalSetting<T> {
    return addPositionalSetting(X, value)
}

public fun <T> PlotContext.x(
    column: ColumnReference<T>,
    parameters: LetsPlotPositionalMappingParameters<T>.() -> Unit = {}
): PositionalMapping<T> {
    return addPositionalMapping<T>(X, column.name(), LetsPlotPositionalMappingParameters<T>().apply(parameters))
}


public fun <T> PlotContext.y(value: T): PositionalSetting<T> {
    return addPositionalSetting(Y, value)
}

public fun <T> PlotContext.y(
    column: ColumnReference<T>,
    parameters: LetsPlotPositionalMappingParameters<T>.() -> Unit = {}
): PositionalMapping<T> {
    return addPositionalMapping<T>(Y, column.name(), LetsPlotPositionalMappingParameters<T>().apply(parameters))
}