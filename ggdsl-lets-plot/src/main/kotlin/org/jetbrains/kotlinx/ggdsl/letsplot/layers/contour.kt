package org.jetbrains.kotlinx.ggdsl.letsplot.layers


import org.jetbrains.kotlinx.ggdsl.letsplot.LetsPlotGeom

/* TODO
@PublishedApi

 */
val CONTOUR = LetsPlotGeom("contour")

/*
@PlotDslMarker
// todo move x/y?
class ContourContext(
    parent: LayerCollectorContext,
    bins: Bins?
) : WithBinsContext(bins) {
    @PublishedApi
    internal val _x = XAes(this)
    @PublishedApi
    internal val _y = YAes(this)
    @PublishedApi
    internal val _z = ZAes(this)

    val x = XDummyAes(this)
    val y = YDummyAes(this)
    val z = ZDummyAes(this)

    val alpha = AlphaAes(this)
    /* TODO */
    val lineColor = ColorAes(this)
    val lineType = LineTypeAes(this)
    val lineWidth = WidthAes(this)

    object Statistics {
        val X = ContourStat.X
        val Y = ContourStat.Y
        val LEVEL = ContourStat.Level
        val GROUP = ContourStat.Group
    }

    val Stat = Statistics



   // todo speed, flow - mi ne znaem chto eto takoe, esli bi mi znali


    inline operator fun <reified DomainType : Any> ScalablePositionalAes.invoke(
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

    inline operator fun <reified DomainType : Any, RangeType : Any> MappableNonPositionalAes<RangeType>.invoke(
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

    private val _bins = BinsAes(this)
    private val binWidth = BinWidthAes(this)

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

inline fun <reified TX : Any, reified TY : Any, reified TZ : Any> PlotContext.contour(
    sourceX: ColumnPointer<TX>,
    sourceY: ColumnPointer<TY>,
    sourceZ: ColumnPointer<TZ>,
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

inline fun <reified TX : Any, reified TY : Any, reified TZ : Any> PlotContext.contour(
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

