/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.layers


/* TODO
@PublishedApi

 */
//public val HISTOGRAM: LetsPlotGeom = LetsPlotGeom("histogram")
/*

/*@PlotDslMarker*/
// todo move x/y?
class HistogramContext(
    parent: LayerCollectorContext,
=======
public val HISTOGRAM: LetsPlotGeom = LetsPlotGeom("histogram")


/*@PlotDslMarker*/
// todo move x/y?
public class HistogramContext(
    override var data: MutableNamedData,
>>>>>>> main
    bins: Bins?,
    boundary: Double?,
    center: Double?,
) : WithBinsContext(bins) {
    init {
        boundary?.let {
            boundary(it)
        }
        center?.let {
            center(it)
        }
    }

    @PublishedApi
    internal val _x: XAes get() = XAes(this)
    public val x: XDummyAes get() = XDummyAes(this)

    public val y: YAes get() = YAes(this)

    public val alpha: AlphaAes get() = AlphaAes(this)
    public val fillColor: FillAes get() = FillAes(this)
    public val borderLineColor: ColorAes get() = ColorAes(this)
    public val borderLineWidth: SizeAes get() = SizeAes(this)

    public object Statistics {
        public val X: BinStat.X = BinStat.X
        public val COUNT: BinStat.Count = BinStat.Count
        public val DENSITY: BinStat.Density = BinStat.Density
    }

    public val Stat: Statistics = Statistics

    // todo weight

    @PublishedApi
    internal val center: CenterAes get() = CenterAes(this)

    @PublishedApi
    internal val boundary: BoundaryAes get() = BoundaryAes(this)


    public inline operator fun <reified DomainType> ScalablePositionalAes.invoke(
        stat: BinStat<DomainType>
    ): ScaledUnspecifiedDefaultPositionalMapping<DomainType> {
        val mapping = ScaledUnspecifiedDefaultPositionalMapping(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

    public inline operator fun <reified DomainType, RangeType> MappableNonPositionalAes<RangeType>.invoke(
        stat: BinStat<DomainType>
    ): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
        val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

}

<<<<<<< HEAD
inline fun <reified T> PlotContext.histogram(
    source: ColumnPointer<T>,
=======
public inline fun <reified T> PlotContext.histogram(
    source: DataSource<T>,
>>>>>>> main
    bins: Bins? = null,
    boundary: Double? = null,
    center: Double? = null,
    block: HistogramContext.() -> Unit
) {
    layers.add(
        HistogramContext(data, bins, boundary, center)
            .apply {
                copyFrom(this@histogram)
                _x(source)
            }
            .apply(block)
            .toLayer(HISTOGRAM)
    )
}

public inline fun <reified T> PlotContext.histogram(
    source: Iterable<T>,
    bins: Bins? = null,
    boundary: Double? = null,
    center: Double? = null,
    block: HistogramContext.() -> Unit
) {
    layers.add(
        HistogramContext(data, bins, boundary, center)
            .apply {
                copyFrom(this@histogram)
                _x(source)
            }
            .apply(block)
            .toLayer(HISTOGRAM)
    )
}


 */

