@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package org.jetbrains.kotlinx.kandy.letsplot.layers.builders.aes

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.kandy.dsl.internal.dataframe.addNonPositionalMapping
import org.jetbrains.kotlinx.kandy.ir.bindings.NonPositionalMapping
import org.jetbrains.kotlinx.kandy.letsplot.internal.LetsPlotNonPositionalMappingParametersContinuous
import org.jetbrains.kotlinx.kandy.letsplot.internal.SPACER_WIDTH
import kotlin.reflect.KProperty

/**
 * Interface for managing the `spacerWidth` aesthetic.
 *
 * Affects the line width between sectors. Spacers are not applied
 * to exploded sectors and to sides of adjacent sectors.
 */
@Suppress("INVISIBLE_MEMBER")
public interface WithSpacerWidth : WithAes {

    /**
     *
     * @property stroke a numeric value that represents the spacer width.
     */
    public var spacerWidth: Number?
        get() = null
        set(value) {
            bindingHandler.addNonPositionalSetting(SPACER_WIDTH, value?.toDouble())
        }

}