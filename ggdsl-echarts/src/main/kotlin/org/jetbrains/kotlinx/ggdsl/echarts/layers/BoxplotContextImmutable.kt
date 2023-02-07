/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.echarts.layers

import org.jetbrains.kotlinx.ggdsl.dsl.internal.LayerCollectorContextImmutable
import org.jetbrains.kotlinx.ggdsl.dsl.internal.PlotDslMarker
import org.jetbrains.kotlinx.ggdsl.echarts.features.animation.AnimationBoxplotCandlestick
import org.jetbrains.kotlinx.ggdsl.echarts.features.animation.AnimationEasing

@PlotDslMarker
public class BoxplotContextImmutable(parent: LayerCollectorContextImmutable) : EchartsLayerContextImmutable(parent) {

    /**
     * Animation options settings for [boxplot][boxplot].
     * If a property isn't set or set to null, a default value will be used.
     *
     * * [duration][AnimationBoxplotCandlestick.duration] - duration of the first animation.
     * By default `1000`.
     * * [easing][AnimationBoxplotCandlestick.easing] -
     * [easing effect][AnimationEasing] used for the first animation.
     * By default `cubicOut`.
     * * [delay][AnimationBoxplotCandlestick.delay] - delay before updating the first animation.
     * By default `0`.
     *
     * ```kotlin
     * plot {
     *  boxplot {
     *      animation {
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
    public val animation: AnimationBoxplotCandlestick = AnimationBoxplotCandlestick()
}