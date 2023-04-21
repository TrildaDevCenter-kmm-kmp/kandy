/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.dsl.internal

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.kandy.ir.Layer
import org.jetbrains.kotlinx.kandy.ir.Plot
import org.jetbrains.kotlinx.kandy.ir.aes.AesName
import org.jetbrains.kotlinx.kandy.ir.bindings.*
import org.jetbrains.kotlinx.kandy.ir.feature.FeatureName
import org.jetbrains.kotlinx.kandy.ir.feature.LayerFeature
import org.jetbrains.kotlinx.kandy.ir.feature.PlotFeature
import org.jetbrains.kotlinx.kandy.ir.geom.Geom

/**
 * Internal collector of mappings and settings.
 *
 * @property mappings [MutableMap] of aesthetic names to their mappings.
 * @property settings [MutableMap] of aesthetic names to their settings.
 * @property freeScales [MutableMap] of aesthetic names to their free scales.
 */
public class BindingCollector() {
    public val mappings: MutableMap<AesName, Mapping> = mutableMapOf()
    public val settings: MutableMap<AesName, Setting> = mutableMapOf()
    public val freeScales: MutableMap<AesName, FreeScale> = mutableMapOf()
}

/**
 * Base plotting DSL context.
 *
 * @property plotContext top [PlotContext] of this context.
 * @property datasetIndex index of context dataset in the [PlotContext.datasetHandlers].
 * @property datasetHandler handler of context dataset.
 */
public interface BaseContext {
    public val plotContext: PlotContext
    public val datasetIndex: Int

    public val datasetHandler: DatasetHandler
        get() = plotContext.datasetHandlers[datasetIndex]
}

/**
 * Context that defines the configuration of the layer.
 *
 * @param parent parental [LayerCollectorContext].
 * @property features [MutableMap] of feature names to corresponding layer features.
 */
public abstract class LayerContext(parent: LayerCollectorContext) : BindingContext {
    override val bindingCollector: BindingCollector = BindingCollector()
    override val datasetIndex: Int = parent.datasetIndex
    public val features: MutableMap<FeatureName, LayerFeature> = mutableMapOf()
    override val plotContext: PlotContext = parent.plotContext
}

/**
 * Context with layers collecting.
 *
 * @property layers layers buffer.
 */
public interface LayerCollectorContext : BaseContext {
    public val layers: MutableList<Layer>

    /**
     * Creates a layers from [LayerContext] and adds it to the buffer.
     *
     * @param context [LayerContext] with bindings of a new layer.
     * @param geom [Geom] of a new layer.
     */
    public fun addLayer(context: LayerContext, geom: Geom) {
        layers.add(
            Layer(
                datasetIndex,
                geom,
                context.bindingCollector.mappings,
                context.bindingCollector.settings,
                context.features,
                context.bindingCollector.freeScales
            )
        )
    }
}

/**
 * Context with a grouped dataset.
 */
public class GroupedContext(
    override val datasetIndex: Int,
    override val plotContext: LayerPlotContext
) : LayerCollectorContext {
    override val layers: MutableList<Layer> = plotContext.layers
}

/**
 * Top plotting context which allows to configure and create a [Plot].
 *
 * @property datasetHandlers buffer of plot datasets.
 * @property features
 */
public interface PlotContext : BindingContext {
    public val datasetHandlers: MutableList<DatasetHandler>
    public val features: MutableMap<FeatureName, PlotFeature>

    /**
     * Creates [Plot] configured by this context.
     *
     * @return new [Plot].
     */
    public fun toPlot(): Plot
}

/**
 *  [PlotContext] that directly collects layers and creates [Plot] from them.
 */
public interface LayerPlotContext : LayerCollectorContext, PlotContext {
    public override fun toPlot(): Plot {
        return Plot(
            datasetHandlers.map { it.data() },
            layers,
            bindingCollector.mappings,
            bindingCollector.settings,
            features,
            bindingCollector.freeScales
        )
    }
}

/**
 * Context with bindings collecting.
 *
 * @property bindingCollector collector of context bindings.
 */
public interface BindingContext : BaseContext {
    public override val plotContext: PlotContext
    public override val datasetIndex: Int
    public val bindingCollector: BindingCollector

    /**
     * Adds [NonPositionalSetting] for given non-positional aes and value.
     *
     * @param aesName name of aes.
     * @param value assigned value.
     * @param DomainType type of value.
     */
    public fun <DomainType> addNonPositionalSetting(aesName: AesName, value: DomainType): NonPositionalSetting<DomainType> {
        return NonPositionalSetting(aesName, value).also {
            bindingCollector.settings[aesName] = it
        }
    }

    /**
     * Adds [PositionalSetting] for given positional aes and value.
     *
     * @param aesName name of aes.
     * @param value assigned value.
     * @param DomainType type of value.
     */
    public fun <DomainType> addPositionalSetting(aesName: AesName, value: DomainType): PositionalSetting<DomainType> {
        return PositionalSetting(aesName, value).also {
            bindingCollector.settings[aesName] = it
        }
    }

    /**
     * Adds [PositionalMapping] for given positional aes from given [List] of values.
     *
     * @param aesName name of aes.
     * @param values [List] of mapped values.
     * @param parameters mapping parameters, optional.
     * @param DomainType type of values.
     */
    public fun <DomainType> addPositionalMapping(
        aesName: AesName, values: List<DomainType>, name: String?, parameters: PositionalMappingParameters<DomainType>?
    ): PositionalMapping<DomainType> {
        val columnID = datasetHandler.addColumn(values, name ?: aesName.name)
        return PositionalMapping<DomainType>(aesName, columnID, parameters).also {
            bindingCollector.mappings[aesName] = it
        }
    }

    /**
     * Adds [PositionalMapping] for given positional aes from column of dataset referenced by given id.
     *
     * @param aesName name of aes.
     * @param columnID name of mapped column of dataset.
     * @param parameters mapping parameters, optional.
     * @param DomainType type of values.
     */
    public fun <DomainType> addPositionalMapping(
        aesName: AesName, columnID: String, parameters: PositionalMappingParameters<DomainType>?
    ): PositionalMapping<DomainType> {
        val newColumnID = datasetHandler.takeColumn(columnID)
        return PositionalMapping<DomainType>(aesName, newColumnID, parameters).also {
            bindingCollector.mappings[aesName] = it
        }
    }

    /**
     * Adds [PositionalMapping] for given positional aes from given [DataColumn] of values.
     *
     * @param aesName name of aes.
     * @param values mapped column.
     * @param parameters mapping parameters, optional.
     * @param DomainType type of values.
     */
    public fun <DomainType> addPositionalMapping(
        aesName: AesName, values: DataColumn<DomainType>, parameters: PositionalMappingParameters<DomainType>?
    ): PositionalMapping<DomainType> {
        val columnID = datasetHandler.addColumn(values)
        return PositionalMapping<DomainType>(aesName, columnID, parameters).also {
            bindingCollector.mappings[aesName] = it
        }
    }

    /**
     * Adds [NonPositionalMapping] for given non-positional aes from given [List] of values.
     *
     * @param aesName name of aes.
     * @param values [List] of mapped values.
     * @param parameters mapping parameters, optional.
     * @param DomainType type of values.
     */
    public fun <DomainType, RangeType> addNonPositionalMapping(
        aesName: AesName,
        values: List<DomainType>,
        name: String?,
        parameters: NonPositionalMappingParameters<DomainType, RangeType>?
    ): NonPositionalMapping<DomainType, RangeType> {
        val columnID = datasetHandler.addColumn(values, name ?: aesName.name)
        return NonPositionalMapping<DomainType, RangeType>(aesName, columnID, parameters).also {
            bindingCollector.mappings[aesName] = it
        }
    }

    /**
     * Adds [NonPositionalMapping] for given positional aes from given [DataColumn] of values.
     *
     * @param aesName name of aes.
     * @param values mapped column.
     * @param parameters mapping parameters, optional.
     * @param DomainType type of values.
     */
    public fun <DomainType, RangeType> addNonPositionalMapping(
        aesName: AesName,
        values: DataColumn<DomainType>,
        parameters: NonPositionalMappingParameters<DomainType, RangeType>?
    ): NonPositionalMapping<DomainType, RangeType> {
        val columnID = datasetHandler.addColumn(values)
        return NonPositionalMapping<DomainType, RangeType>(aesName, columnID, parameters).also {
            bindingCollector.mappings[aesName] = it
        }
    }

    /**
     * Adds [NonPositionalMapping] for given non-positional aes from column of dataset referenced by given id.
     *
     * @param aesName name of aes.
     * @param columnID name of mapped column of dataset.
     * @param parameters mapping parameters, optional.
     * @param DomainType type of values.
     */
    public fun <DomainType, RangeType> addNonPositionalMapping(
        aesName: AesName,
        columnID: String,
        parameters: NonPositionalMappingParameters<DomainType, RangeType>?
    ): NonPositionalMapping<DomainType, RangeType> {
        val newColumnID = datasetHandler.takeColumn(columnID)
        return NonPositionalMapping<DomainType, RangeType>(aesName, newColumnID, parameters).also {
            bindingCollector.mappings[aesName] = it
        }
    }

    /**
     * Adds [PositionalFreeScale] for given positional aes.
     *
     * @param aesName name of aes.
     * @param parameters mapping parameters.
     * @param DomainType scale domain type.
     */
    public fun <DomainType> addPositionalFreeScale(
        aesName: AesName,
        parameters: PositionalMappingParameters<DomainType>
    ) {
        bindingCollector.freeScales[aesName] = PositionalFreeScale<DomainType>(aesName, parameters)
    }
}

/**
 * Nested context. [bindingCollector], [plotContext] and [datasetIndex] are inherited from parent.
 *
 * @property parentContext parental [BindingContext].
 */
public interface SubBindingContext : BindingContext {
    public val parentContext: BindingContext
    override val bindingCollector: BindingCollector
        get() = parentContext.bindingCollector
    override val plotContext: PlotContext
        get() = parentContext.plotContext
    override val datasetIndex: Int
        get() = parentContext.datasetIndex
}