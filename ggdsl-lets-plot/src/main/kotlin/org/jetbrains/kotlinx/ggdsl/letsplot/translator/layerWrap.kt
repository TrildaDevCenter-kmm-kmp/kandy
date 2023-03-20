package org.jetbrains.kotlinx.ggdsl.letsplot.translator

import org.jetbrains.kotlinx.ggdsl.ir.Layer
import org.jetbrains.kotlinx.ggdsl.ir.aes.AesName
import org.jetbrains.kotlinx.ggdsl.ir.bindings.Mapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.Setting
import org.jetbrains.kotlinx.ggdsl.ir.data.GroupedData
import org.jetbrains.kotlinx.ggdsl.ir.data.TableData
import org.jetbrains.letsPlot.intern.Feature


internal fun Layer.wrap(
    featureBuffer: MutableList<Feature>,
    datasets: List<TableData>,
    globalMappings: Map<AesName, Mapping>,
    globalSettings: Map<AesName, Setting>,
) {
    val dataset = if (datasetIndex == 0) {
        null
    } else {
        datasets[datasetIndex]
    }
    val addGroups = datasets[datasetIndex] is GroupedData
    val groupKeys = (datasets[datasetIndex] as? GroupedData)?.keys
    //todo
    val mappings = if (datasetIndex == 0) {
        globalMappings + mappings
    } else {
        mappings
    }
    val settings = if (datasetIndex == 0) {
        globalSettings + settings
    } else {
        settings
    }
    val df = (datasets[datasetIndex]).dataFrame()
    featureBuffer.add(LayerWrapper(this, addGroups, dataset?.wrap(), mappings, settings, groupKeys))
    freeScales.forEach { (_, freeScale) -> freeScale.wrap(featureBuffer) }
    mappings.forEach { (_, mapping) ->
        //todo group
        mapping.wrapScale(df[mapping.columnID].type(), groupKeys)?.let { featureBuffer.add(it) }
    }
}
