package org.jetbrains.kotlinx.kandy.echarts.translator

import kotlinx.datetime.*
import org.jetbrains.kotlinx.dataframe.AnyCol
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.kandy.dsl.internal.dataframe.GroupedData
import org.jetbrains.kotlinx.kandy.dsl.internal.dataframe.NamedData
import org.jetbrains.kotlinx.kandy.echarts.layers.aes.NAME
import org.jetbrains.kotlinx.kandy.echarts.layers.aes.X
import org.jetbrains.kotlinx.kandy.echarts.layers.aes.Y
import org.jetbrains.kotlinx.kandy.echarts.layers.*
import org.jetbrains.kotlinx.kandy.echarts.scale.EchartsPositionalMappingParameters
import org.jetbrains.kotlinx.kandy.echarts.translator.option.*
import org.jetbrains.kotlinx.kandy.echarts.translator.option.series.*
import org.jetbrains.kotlinx.kandy.echarts.translator.option.series.settings.Encode
import org.jetbrains.kotlinx.kandy.echarts.translator.option.util.Element
import org.jetbrains.kotlinx.kandy.ir.Layer
import org.jetbrains.kotlinx.kandy.ir.Plot
import org.jetbrains.kotlinx.kandy.ir.aes.Aes
import org.jetbrains.kotlinx.kandy.ir.bindings.*
import org.jetbrains.kotlinx.kandy.ir.data.TableData
import org.jetbrains.kotlinx.kandy.ir.scale.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf

internal fun Plot.toOption(): Option = Parser(this).parse()

@Suppress("UNCHECKED_CAST")
internal fun <T> Map<Aes, Setting>.getNPSValue(key: Aes): T? {
    return (this[key] as? NonPositionalSetting<*>)?.value as? T
}

internal class Parser(plot: Plot) {

    // TODO(add preprocessing with fill na for datasets)
    // elements from plot
    private val datasets: MutableList<TableData> = plot.datasets as MutableList<TableData>
    private val globalMappings = plot.globalMappings
    private val layers = plot.layers
    private val features = plot.features

    // items for option
    private val xAxis = mutableListOf<Axis>()
    private val yAxis = mutableListOf<Axis>()

    private val typeMapping: Map<KType, AxisType> = mapOf(
        typeOf<String>() to AxisType.CATEGORY, typeOf<String?>() to AxisType.CATEGORY,
        typeOf<Char>() to AxisType.CATEGORY, typeOf<Char?>() to AxisType.CATEGORY,
        typeOf<Number>() to AxisType.VALUE, typeOf<Number?>() to AxisType.VALUE,
        typeOf<LocalDateTime>() to AxisType.TIME, typeOf<LocalDateTime?>() to AxisType.TIME,
        typeOf<java.time.LocalDateTime>() to AxisType.TIME, typeOf<java.time.LocalDateTime?>() to AxisType.TIME,
        typeOf<LocalDate>() to AxisType.TIME, typeOf<LocalDate?>() to AxisType.TIME,
        typeOf<java.time.LocalDate>() to AxisType.TIME, typeOf<java.time.LocalDate?>() to AxisType.TIME,
        typeOf<LocalTime>() to AxisType.CATEGORY, typeOf<LocalTime?>() to AxisType.CATEGORY,
        typeOf<Month>() to AxisType.CATEGORY, typeOf<Month?>() to AxisType.CATEGORY,
        typeOf<DayOfWeek>() to AxisType.CATEGORY, typeOf<DayOfWeek?>() to AxisType.CATEGORY,
        typeOf<java.time.LocalTime>() to AxisType.CATEGORY, typeOf<java.time.LocalTime?>() to AxisType.CATEGORY,
        typeOf<java.time.Month>() to AxisType.CATEGORY, typeOf<java.time.Month?>() to AxisType.CATEGORY,
        typeOf<java.time.DayOfWeek>() to AxisType.CATEGORY, typeOf<java.time.DayOfWeek?>() to AxisType.CATEGORY
    )


    internal fun parse(): Option {
        val layout = (features[EChartsLayout.FEATURE_NAME] as? EChartsLayout)
        val mainDataset = datasets.first()

        with(globalMappings) {
            this[X]?.also { xAxis.add(it.toAxis(mainDataset.getType(it))) }
            this[Y]?.also { yAxis.add(it.toAxis(mainDataset.getType(it))) }
        }

        val title = layout?.title?.toEchartsTitle()
        val legend = layout?.legend?.toEchartsLegend()
        val grid = layout?.grid?.toEchartsGrid()
        val tooltip = layout?.tooltip?.toEchartsTooltip()
        val textStyle = layout?.textStyle?.toTextStyle()
        val animation = layout?.animation?.toAnimationPlotFeature()

        val visualMaps = mutableListOf<VisualMap>()
        val series = mutableListOf<Series>()

        layers.forEachIndexed { index, layer ->
            layer.mappings.forEach { (aes, mapping) ->
                val df = datasets[layer.datasetIndex]
                when (aes) {
                    X -> xAxis.add(mapping.toAxis(df.getType(mapping)))
                    Y -> yAxis.add(mapping.toAxis(df.getType(mapping)))
                    else -> {
                        val scale = mapping.parameters?.scale
                        if (scale is NonPositionalScale<*, *>) {
                            visualMaps.add(
                                scale.toVisualMap(
                                    aes,
                                    mapping.columnID,
                                    index,
                                    df[mapping].toList(),
                                    visualMaps.size,
                                    df.getType(mapping)
                                )
                            )
                        }
                    }
                }
            }

            when {
                // TODO - if datasetIndex > 0, what to do with dataset and encode
                layer.datasetIndex == 0 && datasets[layer.datasetIndex] !is GroupedData -> series.add(layer.toSeries())
                datasets[layer.datasetIndex] is GroupedData -> series.addAll(layer.toGroupedSeries())
            }
        }

        val source = if (mainDataset is NamedData && mainDataset.dataFrame.isNotEmpty()) {
            listOf(mainDataset.dataFrame.columnNames()) + mainDataset.dataFrame.map {
                it.values().map { l -> l?.toString() }
            }
        } else null
        val dataset = source?.let { Dataset(source = source) }

        return Option(
            title = title,
            legend = legend,
            grid = grid,
            xAxis = xAxis.firstOrNull(),
            yAxis = yAxis.firstOrNull(),
            visualMap = visualMaps.ifEmpty { null },
            tooltip = tooltip,
            dataset = dataset,
            series = series.ifEmpty { null },
            textStyle = textStyle,
            animation = animation?.enable,
            animationThreshold = animation?.threshold,
            animationDuration = animation?.duration,
            animationEasing = animation?.easing,
            animationDelay = animation?.delay,
            plotSize = layout?.size ?: (800 to 600)
        )
    }

    /**
     * Retrieves the [AnyCol] from [DataFrame] data corresponding to the specified [mapping].
     *
     * @param mapping The [Mapping] object containing the `columnID` to map to the appropriate data in [TableData].
     * @return The [AnyCol] data corresponding to the specified [mapping].
     */
    private operator fun TableData.get(mapping: Mapping): AnyCol = when (this) {
        is NamedData -> this.dataFrame[mapping.columnID]
        is GroupedData -> this.dataFrame[mapping.columnID]
        else -> error("")
    }

    private fun TableData.getType(mapping: Mapping): KType = when (this) {
        is NamedData -> this.dataFrame[mapping.columnID].type()
        is GroupedData -> this.dataFrame[mapping.columnID].type()
        else -> error("")
    }

    private fun DataFrame<*>.fillNA(mapping: Mapping): DataFrame<*> {
        val nullValue = when (val scale = mapping.parameters?.scale) {
            is PositionalContinuousScale<*> -> scale.nullValue
            is NonPositionalContinuousScale<*, *> -> scale.nullValue
            else -> null
        }
        return nullValue?.let { nv -> this.fillNA(mapping.columnID).with { nv } } ?: this
    }

    private fun Mapping.toAxis(ktype: KType): Axis {
        this as PositionalMapping<*>
        val params = this.parameters as EchartsPositionalMappingParameters
        val axis = params.axis
        val axisScale = params.scale
        var min: String? = null
        var max: String? = null
        val type = when (axisScale) {
            is PositionalCategoricalScale<*> -> AxisType.CATEGORY
            is PositionalContinuousScale<*> -> {
                min = axisScale.min?.toString() // TODO(String?)
                max = axisScale.max?.toString()
                AxisType.VALUE
            }

            is PositionalDefaultScale -> typeMapping[ktype] ?: AxisType.VALUE
        }
        return Axis(name = axis.name, show = axis.show, type = type.value, min = min, max = max)
    }

    private fun Layer.toSeries(): Series {
        val x = mappings[X]?.columnID ?: globalMappings[X]?.columnID
        val y = mappings[Y]?.columnID ?: globalMappings[Y]?.columnID

        val encode = Encode(x, y).takeIf { it.isNotEmpty() }
        val name = settings.getNPSValue(NAME) ?: buildString {
            x?.let { append(it) }
            if (x != null && y != null) append(" ")
            y?.let { append(it) }
        }.takeIf { it.isNotEmpty() }

        return getSeries(name, encode, null)
    }

    private fun Layer.toGroupedSeries(): List<Series> {
        val groupedData = datasets[datasetIndex] as GroupedData
        val x = this.mappings[X]?.columnID ?: globalMappings[X]?.columnID
        val y = this.mappings[Y]?.columnID ?: globalMappings[Y]?.columnID
        val groupedSeries = mutableListOf<Series>()
        groupedData.keys.forEach { columnName ->
            groupedData.groupBy.map { _ ->
                val data = group.select(x!!, y!!).map { it.values().map { l -> Element.of(l) } }
                groupedSeries.add(getSeries(key.getValue(columnName), null, data)) // TODO(aggregate data better)
            }

        }
        return groupedSeries
    }

    private fun Layer.getSeries(name: String?, encode: Encode?, data: List<List<Element?>>? = null): Series =
        when (geom) {
            LINE -> this.toLineSeries(name, encode, data)
            AREA -> this.toAreaSeries(name, encode, data)
            BAR -> this.toBarSeries(name, encode, data)
            PIE -> this.toPieSeries(name, encode, data)
            POINT -> this.toPointSeries(name, encode, data)
            CANDLESTICK -> this.toCandlestickSeries(name, encode, data)
            BOXPLOT -> this.toBoxplotSeries(name, encode, data)
            else -> TODO("exception?")
        }
}