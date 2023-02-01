/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.echarts.layers

import org.jetbrains.kotlinx.ggdsl.dsl.internal.LayerCollectorContextImmutable
// import org.jetbrains.kotlinx.ggdsl.dsl.internal.PlotDslMarker
import org.jetbrains.kotlinx.ggdsl.echarts.aes.*
import org.jetbrains.kotlinx.ggdsl.echarts.features.animation.Animation
import org.jetbrains.kotlinx.ggdsl.echarts.features.animation.AnimationEasing

/**
 * Area settings.
 *
 * @property x mapping data on the x-axis.
 * @property y mapping data on the y-axis.
 * @property color area fill [color][org.jetbrains.kotlinx.ggdsl.util.color.Color].
 * @property position origin [position][org.jetbrains.kotlinx.ggdsl.echarts.settings.AreaPosition] of area. `auto` by default.
 * @property shadowBlur shadow blur size of the area.
 * @property shadowColor shadow [color][org.jetbrains.kotlinx.ggdsl.util.color.Color] of area.
 * @property alpha opacity of area.
 * @property lineColor line [color][org.jetbrains.kotlinx.ggdsl.util.color.Color].
 * @property symbol [symbol][org.jetbrains.kotlinx.ggdsl.echarts.settings.Symbol] on line. Symbols are not shown by default.
 * @property smooth smooth curve. `false` by default.
 * @property lineAlpha line opacity.
 * @property lineWidth line width. `2` by default.
 * @property lineType [line type][org.jetbrains.kotlinx.ggdsl.echarts.settings.LineType]. `solid` by default.
 * @property step step line. `false` by default.
 * @property cap [end points][org.jetbrains.kotlinx.ggdsl.echarts.settings.Cap] of line. `butt` by default.
 * @property lineShadowColor shadow color of line.
 * @property lineShadowBlur shadow blur size of line.
 * @property animation [animation][Animation]
 *
 * @see area
 * @see org.jetbrains.kotlinx.ggdsl.util.color.Color
 * @see org.jetbrains.kotlinx.ggdsl.echarts.settings.AreaPosition
 * @see org.jetbrains.kotlinx.ggdsl.echarts.settings.Symbol
 * @see org.jetbrains.kotlinx.ggdsl.echarts.settings.Cap
 * @see org.jetbrains.kotlinx.ggdsl.echarts.settings.LineType
 * @see Animation
 */
/*@PlotDslMarker*/
public class AreaContextImmutable(parent: LayerCollectorContextImmutable) : EchartsLayerContextImmutable(parent) {
    public val x: XAes = XAes(this)
    public val y: YAes = YAes(this)
    public val color: AreaColorAes = AreaColorAes(this)
    public val position: PositionAes = PositionAes(this)
    public val shadowBlur: AreaShadowBlurAes = AreaShadowBlurAes(this)
    public val shadowColor: AreaShadowColorAes = AreaShadowColorAes(this)
    public val alpha: AreaAlphaAes = AreaAlphaAes(this)

    public val lineColor: LineColorAes = LineColorAes(this)
    public val symbol: SymbolAes = SymbolAes(this)
    public val smooth: SmoothAes = SmoothAes(this)
    public val lineAlpha: LineAlphaAes = LineAlphaAes(this)
    public val lineWidth: WidthAes = WidthAes(this)
    public val lineType: LineTypeAes = LineTypeAes(this)
    public val step: StepAes = StepAes(this)
    public val cap: CapAes = CapAes(this)
    public val lineShadowColor: LineShadowColorAes = LineShadowColorAes(this)
    public val lineShadowBlur: LineShadowBlurAes = LineShadowBlurAes(this)

    /**
     * Animation options settings for [area][area].
     * If a property isn't set or set to null, a default value will be used.
     *
     * * [enable][Animation.enable] - responsible for enabling animation.
     * By default `true`.
     * * [threshold][Animation.threshold] - sets a graphic number threshold for animation.
     * Animation will be disabled when graphic number exceeds a threshold.
     * By default `2000`.
     * * [duration][Animation.duration] - duration of the first animation.
     * By default `1000`.
     * * [easing][Animation.easing] - [easing effect][AnimationEasing] used for the first animation.
     * By default `cubicOut`.
     * * [delay][Animation.delay] - delay before updating the first animation.
     * By default `0`.
     *
     * ```kotlin
     * plot {
     *  area {
     *      animation {
     *          enable = true
     *          threshold = 2000
     *          duration = 1000
     *          easing = AnimationEasing.CUBIC_OUT
     *          delay = 0
     *      }
     *  }
     * }
     * ```
     *
     * @see AnimationEasing
     */
    public val animation: Animation = Animation()
}