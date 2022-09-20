package org.jetbrains.kotlinx.ggdsl.echarts.animation

/**
 * Animation options settings.
 *
 * - [enable][AnimationFeature.enable] - whether to enable animation.
 * - [threshold][AnimationFeature.threshold] - whether to set a graphic number threshold to animation.
 * Animation will be turned off when a graphic number is larger than threshold.
 * - [duration][AnimationFeature.duration] - the duration of the first animation.
 * - [easing][AnimationFeature.easing] -the easing method used for the first animation.
 * - [delay][AnimationFeature.delay] - the delay before updating the first animation.
 */
public fun org.jetbrains.kotlinx.ggdsl.dsl.PlotContext.animation(block: AnimationFeature.() -> Unit) {
    features[AnimationFeature.FEATURE_NAME] = AnimationFeature().apply(block)
}
