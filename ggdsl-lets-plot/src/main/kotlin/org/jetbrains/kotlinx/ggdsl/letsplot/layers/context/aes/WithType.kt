package org.jetbrains.kotlinx.ggdsl.letsplot.layers.context.aes

import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.ggdsl.dsl.internal.BindingContext
import org.jetbrains.kotlinx.ggdsl.ir.bindings.NonPositionalMapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.NonPositionalSetting
import org.jetbrains.kotlinx.ggdsl.letsplot.internal.LINE_TYPE
import org.jetbrains.kotlinx.ggdsl.letsplot.internal.LetsPlotNonPositionalMappingParameters
import org.jetbrains.kotlinx.ggdsl.letsplot.util.linetype.LineType

public interface WithType : BindingContext {
    public var type: LineType?
        get() = null
        set(value) {
            bindingCollector.settings[LINE_TYPE] = NonPositionalSetting(LINE_TYPE, value)
        }
    public fun <T> type(
        column: ColumnReference<T>,
        parameters: LetsPlotNonPositionalMappingParameters<T, LineType>.() -> Unit = {}
    ): NonPositionalMapping<T, LineType> {
        return addNonPositionalMapping<T, LineType>(
            LINE_TYPE,
            column.name(),
            LetsPlotNonPositionalMappingParameters<T, LineType>().apply(parameters)
        )
    }
}