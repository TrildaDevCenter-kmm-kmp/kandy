package org.jetbrains.kotlinx.ggdsl.dataframe.letsplot.layers
/*
import org.jetbrains.kotlinx.dataframe.columns.ColumnReference
import org.jetbrains.kotlinx.ggdsl.dataframe.toColRef
import org.jetbrains.kotlinx.ggdsl.dsl.PlotContext
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.QQ2Context
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.qq2

inline fun <reified T : Any, reified R: Any> PlotContext.qq2(
    sourceX: ColumnReference<T>,
    sourceY: org.jetbrains.kotlinx.ggdsl.ir.data.ColumnPointer<R>,
    block: QQ2Context.() -> Unit
) = qq2(sourceX.toColRef(), sourceY, block)

inline fun <reified T : Any, reified R: Any> PlotContext.qq2(
    sourceX: org.jetbrains.kotlinx.ggdsl.ir.data.ColumnPointer<T>,
    sourceY: ColumnReference<R>,
    block: QQ2Context.() -> Unit
) = qq2(sourceX, sourceY.toColRef(), block)

inline fun <reified T : Any, reified R: Any> PlotContext.qq2(
    sourceX: ColumnReference<T>,
    sourceY: ColumnReference<R>,
    block: QQ2Context.() -> Unit
) = qq2(sourceX.toColRef(), sourceY.toColRef(), block)

 */