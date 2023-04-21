/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.letsplot.layers

import org.jetbrains.kotlinx.kandy.dsl.internal.LayerCollectorContext
import org.jetbrains.kotlinx.kandy.letsplot.internal.LetsPlotGeom
import org.jetbrains.kotlinx.kandy.letsplot.layers.context.PointsContext

@PublishedApi
internal val POINT: LetsPlotGeom = LetsPlotGeom("point")

/**
 * Adds a new points layer.
 *
 * Creates a context in which you can configure layer. Within it, you can set mappings and settings
 * on aesthetic attributes. Mappings allow you to set a relationship between data and attribute values,
 * while settings allow you to assign a constant value to the attributes.
 *
 * Mapping can be performed via method with name of corresponding aes.
 * Setting for non-positional attributes can be performed with simple assignment of variable with name of aes.
 * Setting for positional attributes can be performed with `.constant()` method of special property with
 * the same name as the attribute.
 *
 * Points aesthetics:
 * * `x`
 * * `y`
 * * `color`
 * * `symbol`
 * * `size`
 * * `alpha`
 * * `fillColor` (only for "FILLED" symbols)
 *
 * Example:
 *
 * ```
 * points {
 *    // positional mapping
 *    x(time) {
 *       ... // some mapping parameters
 *    }
 *    // positional setting
 *    y.constant(12.5)
 *
 *    // non-positional setting
 *    color = Color.BLUE
 *    // non-positional mapping
 *    size("spread")
 * }
 * ```
 */
public inline fun LayerCollectorContext.points(block: PointsContext.() -> Unit) {
    addLayer(PointsContext(this).apply(block), POINT)
}