package org.jetbrains.kotlinx.ggdsl.letsplot.layers

import org.jetbrains.kotlinx.ggdsl.dsl.*
import org.jetbrains.kotlinx.ggdsl.ir.data.DataSource
import org.jetbrains.kotlinx.ggdsl.letsplot.*
import org.jetbrains.kotlinx.ggdsl.letsplot.util.statParameters.BandWidth
import org.jetbrains.kotlinx.ggdsl.letsplot.util.statParameters.Kernel


val DENSITY = LetsPlotGeom("density")

// todo stats
@PlotDslMarker
class DensityContext(
    override var data: MutableNamedData,
    kernel: Kernel?,
    bandWidth: BandWidth?,
    pointsSampled: Int?,
    trim: Boolean?,
    adjust: Double?,
    fullScanMax: Int?,
) : LayerContext() {

    @PublishedApi
    internal val x = XAes(this)
    @PublishedApi
    internal val y = YAes(this)


    val alpha = AlphaAes(this)
    val fillColor = FillAes(this)
    val borderLineWidth = SizeAes(this)
    val borderLineColor = ColorAes(this)
    val borderLineType = LineTypeAes(this)

    // todo weight

    @PublishedApi
    internal val kernel = KernelAes(this)
    @PublishedApi
    internal val bw = BWAes(this)
    @PublishedApi
    internal val pointsSampled = NumberAes(this)
    @PublishedApi
    internal val trim = TrimAes(this)
    @PublishedApi
    internal val adjust = AdjustAes(this)
    @PublishedApi
    internal val fullScanMax = FullScanMaxAes(this)

    init {
        kernel?.let {
            kernel(it)
        }
        bandWidth?.let {
            bw(it)
        }
        pointsSampled?.let {
            pointsSampled(it)
        }
        trim?.let {
            trim(it)
        }
        adjust?.let {
            adjust(it)
        }
        fullScanMax?.let {
            fullScanMax(it)
        }
    }
    /*
    @PublishedApi
    internal inline fun <reified T : Any> Stat<T>.toDataSource(): DataSource<T> {
        return DataSource(name, typeOf<T>())
    }

    inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
        stat: Stat<DomainType>
    ): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
        val mapping = ScaledUnspecifiedDefaultPositionalMapping(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

    inline operator fun <reified DomainType : Any, RangeType : Any>
            MappableNonPositionalAes<RangeType>.invoke(
        stat: Stat<DomainType>
    ): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
        val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

     */


}

inline fun <reified T : Any> PlotContext.density(
    source: DataSource<T>,
    kernel: Kernel? = null,
    bandWidth: BandWidth? = null,
    pointsSampled: Int? = null,
    trim: Boolean? = null,
    adjust: Double? = null,
    fullScanMax: Int? = null,
    block: DensityContext.() -> Unit,
) {
    layers.add(
        DensityContext(data, kernel, bandWidth, pointsSampled, trim, adjust, fullScanMax)
            .apply {
                copyFrom(this@density)
                x(source)
            }
            .apply(block)
            .toLayer(DENSITY)
    )
}

inline fun <reified T : Any> PlotContext.density(
    source: Iterable<T>,
    kernel: Kernel? = null,
    bandWidth: BandWidth? = null,
    pointsSampled: Int? = null,
    trim: Boolean? = null,
    adjust: Double? = null,
    fullScanMax: Int? = null,
    block: DensityContext.() -> Unit
) {
    layers.add(
        DensityContext(data, kernel, bandWidth, pointsSampled, trim, adjust, fullScanMax)
            .apply {
                copyFrom(this@density)
                x(source)
            }
            .apply(block)
            .toLayer(DENSITY)
    )
}
