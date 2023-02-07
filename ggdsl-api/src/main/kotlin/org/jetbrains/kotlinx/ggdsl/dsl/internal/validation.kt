package org.jetbrains.kotlinx.ggdsl.dsl.internal

import org.jetbrains.kotlinx.ggdsl.dsl.LazyGroupedData
import org.jetbrains.kotlinx.ggdsl.dsl.NamedData
import org.jetbrains.kotlinx.ggdsl.ir.Layer
import org.jetbrains.kotlinx.ggdsl.ir.Plot
import org.jetbrains.kotlinx.ggdsl.ir.aes.AesName
import org.jetbrains.kotlinx.ggdsl.ir.bindings.Mapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.NonScalableNonPositionalMapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.NonScalablePositionalMapping
import org.jetbrains.kotlinx.ggdsl.ir.bindings.ScaledMapping
import org.jetbrains.kotlinx.ggdsl.ir.data.CountedGroupedDataInterface
import org.jetbrains.kotlinx.ggdsl.ir.data.LazyGroupedDataInterface
import org.jetbrains.kotlinx.ggdsl.ir.data.NamedDataInterface
import org.jetbrains.kotlinx.ggdsl.ir.data.TableData
import kotlin.reflect.KType

/**
 * Checks if all columns of dataset are the same size.
 */
public fun NamedData.validate() {
    if (nameToValues.isEmpty()) {
        return
    }
    val valuesLists = nameToValues.values
    val firstSize = valuesLists.first().values.size
    require(valuesLists.all {
        it.values.size == firstSize
    }
    ) {
        "All columns of dataset should be the same size"
    }
}

/**
 * Checks if all group keys are the column names.
 */
public fun LazyGroupedData.validate() {
    keys.forEach {
        require(it in origin.nameToValues.keys) {
            "$it not is the name of the column of the original dataframe"
        }
    }
}

/**
 * Validates layers: validates all mappings in a layer.
 */
public fun Layer.validate(plotDataset: TableData) {
    val columns = (dataset ?: plotDataset).columns()
    mappings.validate(columns)
}

internal fun TableData.columns(): Map<String, KType> {
    return when (this) {
        is NamedDataInterface -> nameToValues.map {
            it.key to it.value.kType
        }.toMap()

        is LazyGroupedDataInterface -> origin.columns()
        is CountedGroupedDataInterface -> this.toLazy().columns()
    }
}
/* TODO
internal fun Map<AesName, Mapping>.validateGroups(groupKys: Set<String>) {
    forEach { (_, mapping) ->
        if (mapping is BaseScaledNonPositionalMapping<*, *>) {
            val columnName = mapping.columnScaled.source.name
            val scale = mapping.columnScaled.scale
            if (scale is Categor)
        }
    }
}

 */

internal fun Map<AesName, Mapping>.validate(columns: Map<String, KType>) {
    val columnNames = columns.keys
    forEach { (_, mapping) ->
        val columnName = when (mapping) {
            is ScaledMapping<*> -> mapping.columnScaled.source.name
            is NonScalableNonPositionalMapping<*> -> mapping.source.name
            is NonScalablePositionalMapping<*> -> mapping.source.name
        }
        require(columnName in columnNames) {
            "No column with name \"$columnName\" found in dataframe with columns $columnNames"
        }
        val expectedType = columns[columnName]
        val actualType = mapping.domainType
        require(expectedType == actualType) {
            "Expected $expectedType as type of the pointer to column \"$columnName\" but actual type is $actualType"
        }
    }
}

/**
 * Checks presence of column with a given name.
 */
public fun TableData.validateColumn(columnName: String) {
    val columns = columns()
    require(columnName in columns) {
        "No column with name \"$columnName\" found in dataframe with columns ${columns.keys}"
    }
}

/**
 * Validates plot: validates all global mappings and layers.
 */
public fun Plot.validate() {
    globalMappings.validate(dataset.columns())
    layers.forEach {
        it.validate(dataset)
    }
}