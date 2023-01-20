/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.letsplot.layers


/* TODO
@PublishedApi

 */
//public val CONTOUR: LetsPlotGeom = LetsPlotGeom("contour")

/*
@PlotDslMarker
// todo move x/y?
<<<<<<< HEAD
class ContourContext(
    parent: LayerCollectorContext,
=======
public class ContourContext(
    override var data: MutableNamedData,
>>>>>>> main
    bins: Bins?
) : WithBinsContext(bins) {
    @PublishedApi
    internal val _x: XAes get() = XAes(this)

    @PublishedApi
    internal val _y: YAes get() = YAes(this)

    @PublishedApi
    internal val _z: ZAes get() = ZAes(this)

    public val x: XDummyAes get() = XDummyAes(this)
    public val y: YDummyAes get() = YDummyAes(this)
    public val z: ZDummyAes get() = ZDummyAes(this)

    public val alpha: AlphaAes get() = AlphaAes(this)

    /* TODO */
    public val lineColor: ColorAes get() = ColorAes(this)
    public val lineType: LineTypeAes get() = LineTypeAes(this)
    public val lineWidth: WidthAes get() = WidthAes(this)

    public object Statistics {
        public val X: ContourStat.X = ContourStat.X
        public val Y: ContourStat.Y = ContourStat.Y
        public val LEVEL: ContourStat.Level = ContourStat.Level
        public val GROUP: ContourStat.Group = ContourStat.Group
    }

    public val Stat: Statistics = Statistics


    // todo speed, flow - mi ne znaem chto eto takoe, esli bi mi znali


    public inline operator fun <reified DomainType> ScalablePositionalAes.invoke(
        stat: ContourStat<DomainType>
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
        stat: ContourStat<DomainType>
    ): ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType> {
        val mapping = ScaledUnspecifiedDefaultNonPositionalMapping<DomainType, RangeType>(
            this.name,
            stat.toDataSource().scaled(),
            typeOf<DomainType>()
        )
        context.bindingCollector.mappings[this.name] = mapping
        return mapping
    }

    /* interface BinOption {
         data class ByNumber(val number: Int) : BinOption
         data class ByWidth(val width: Double) : BinOption
     }

     fun byNumber(number: Int) = BinOption.ByNumber(number)
     fun byWidth(width: Double) = BinOption.ByWidth(width)

     private val _bins get() = BinsAes(this)
     private val binWidth get() = BinWidthAes(this)

     var bins: BinOption? = null
         set(value) {
             when (value) {
                 is BinOption.ByNumber -> {
                     bindingCollector.settings.remove(BIN_WIDTH)
                     _bins(value.number)
                 }

                 is BinOption.ByWidth -> {
                     bindingCollector.settings.remove(BINS)
                     binWidth(value.width)
                 }

                 else -> {
                     bindingCollector.settings.remove(BINS)
                     bindingCollector.settings.remove(BIN_WIDTH)
                 }
             }
             field = null
         }

     */
}

<<<<<<< HEAD
inline fun <reified TX, reified TY, reified TZ> PlotContext.contour(
    sourceX: ColumnPointer<TX>,
    sourceY: ColumnPointer<TY>,
    sourceZ: ColumnPointer<TZ>,
=======
public inline fun <reified TX, reified TY, reified TZ> PlotContext.contour(
    sourceX: DataSource<TX>,
    sourceY: DataSource<TY>,
    sourceZ: DataSource<TZ>,
>>>>>>> main
    bins: Bins? = null,
    block: ContourContext.() -> Unit
) {
    layers.add(
        ContourContext(data, bins)
            .apply {
                copyFrom(this@contour)
                _x(sourceX)
                _y(sourceY)
                _z(sourceZ)
            }
            .apply(block)
            .toLayer(CONTOUR)
    )
}

public inline fun <reified TX, reified TY, reified TZ> PlotContext.contour(
    sourceX: Iterable<TX>,
    sourceY: Iterable<TY>,
    sourceZ: Iterable<TZ>,
    bins: Bins? = null,
    block: ContourContext.() -> Unit
) {
    layers.add(
        ContourContext(data, bins)
            .apply {
                copyFrom(this@contour)
                _x(sourceX)
                _y(sourceY)
                _z(sourceZ)
            }
            .apply(block)
            .toLayer(CONTOUR)
    )
}


 */

