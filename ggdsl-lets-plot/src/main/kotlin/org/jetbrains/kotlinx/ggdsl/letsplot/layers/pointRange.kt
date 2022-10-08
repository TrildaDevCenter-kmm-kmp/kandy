/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.layers

import org.jetbrains.kotlinx.ggdsl.dsl.*
import org.jetbrains.kotlinx.ggdsl.dsl.contexts.BindingContext
import org.jetbrains.kotlinx.ggdsl.dsl.contexts.LayerCollectorContext
import org.jetbrains.kotlinx.ggdsl.dsl.contexts.LayerContext
import org.jetbrains.kotlinx.ggdsl.letsplot.*
import org.jetbrains.kotlinx.ggdsl.letsplot.util.symbol.Symbol
import org.jetbrains.kotlinx.ggdsl.util.color.Color

@PublishedApi
internal val POINT_RANGE: LetsPlotGeom = LetsPlotGeom("pointrange")

@PlotDslMarker
public class InnerPointSubContext(parentContext: BindingContext) {
    public val symbol: ShapeAes = ShapeAes(parentContext)
    public val fillColor: FillAes = FillAes(parentContext)
    public val fatten: FattenAes = FattenAes(parentContext)
}

@PlotDslMarker
public class InnerLineSubContext(parentContext: BindingContext)  {
    public val type: LineTypeAes = LineTypeAes(parentContext)
}

@PlotDslMarker
public class PointRangeContext(parent: LayerCollectorContext) : LayerContext(parent) {
    public val x: XAes get() = XAes(this)
    public val y: YAes get() = YAes(this)
    public val yMin: YMinAes get() = YMinAes(this)
    public val yMax: YMaxAes get() = YMaxAes(this)

    public val alpha: AlphaAes get() = AlphaAes(this)
    public val color: ColorAes get() = ColorAes(this)

    // todo separate????
    public val size: SizeAes get() = SizeAes(this)

    public val innerPoint: InnerPointSubContext get() = InnerPointSubContext(this)

    public inline operator fun InnerPointSubContext.invoke(block: InnerPointSubContext.() -> Unit) {
        apply(block)
    }

    public val innerLine: InnerLineSubContext get() = InnerLineSubContext(this)

    public inline operator fun InnerLineSubContext.invoke(block: InnerLineSubContext.() -> Unit) {
        apply(block)
    }
}

/**
 * Adds a new point range layer.
 *
 * Creates a context in which you can create bindings using aesthetic attribute properties invocation:
 * ```
 * lineRange {
 *    x(source<Double>("time")) // mapping from data source to 'x' coordinate
 *    innerLine.type(LineType.SOLID) // setting of constant line type value
 *    innerPoint {
 *       symbol(Symbol.CIRCLE) // setting of constant symbol value
 *    }
 * }
 * ```
 *
 *  ### Aesthetic attributes:
 *
 *  Positional:
 *
 *  - [ x][PointRangeContext.x]
 *
 *  Initial mappings to positional attributes are inherited from the parent [PlotContext] (if they exist).
 *
 *  Sub-positional:
 *  - [yMin][PointRangeContext.yMin] - lower bound of the error bar
 *  - [yMax][PointRangeContext.yMax] - upper bound of the error bar
 *
 *   Non-positional:
 *  - [alpha][PointRangeContext.alpha] - layer alpha, of the type [Double], mappable
 *  - [color][PointRangeContext.color] - color of the point range, of the type [Color], mappable
 *  - [size][PointRangeContext.size] - width of the line and size of the mid-point, of the type [Double], mappable
 *  - [innerPoint.symbol][InnerPointSubContext.symbol] - symbol of the borderline, of the type [Symbol], mappable.
 *  - [innerPoint.fillColor][InnerPointSubContext.fillColor] - color of the point filling (only for "FILLED" symbols),
 *  of the type [Color], mappable.
 *  - [innerPoint.fatten][InnerPointSubContext.fatten] - a multiplicative factor applied to size
 *  of the middle point, of the type [Double], non-mappable.
 *
 *  // TODO write about inners invocation?
 *  ```
 *  pointRange {
 *     innerLine {
 *        type(LineType.DOTTED)
 *     }
 *     innerPoint {
 *        fillColor(Color.RED)
 *        symbol(Symbol.DIAMOND_FILLED)
 *     }
 *  }
 *  ```
 *
 * // TODO move data overriding to args
 *  By default, the dataset inherited from the parent [PlotContext] is used,
 *  but can be overridden with an assignment to the [data][PointRangeContext.data].
 *
 *  // TODO refer to bindings?
 */

public inline fun LayerCollectorContext.pointRange(block: PointRangeContext.() -> Unit) {
    addLayer(PointRangeContext(this).apply(block), POINT_RANGE)
}
