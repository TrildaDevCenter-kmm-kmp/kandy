package org.jetbrains.kotlinx.ggdsl.letsplot.facet

import org.jetbrains.kotlinx.ggdsl.dsl.PlotContext
import org.jetbrains.kotlinx.ggdsl.dsl.PlotDslMarker
import org.jetbrains.kotlinx.ggdsl.ir.data.DataSource
import org.jetbrains.kotlinx.ggdsl.ir.feature.FeatureName
import org.jetbrains.kotlinx.ggdsl.ir.feature.PlotFeature


public data class OrderDirection internal constructor(val value: Int) {
    public companion object {
        public val ASCENDING: OrderDirection = OrderDirection(1)
        public val DESCENDING: OrderDirection = OrderDirection(-1)
    }
}

public data class Direction internal constructor(val name: String) {
    public companion object {
        public val VERTICAL: Direction = Direction("v")
        public val HORIZONTAL: Direction = Direction("h")
    }
}

public data class ScalesSharing internal constructor(val name: String) {
    public companion object {
        public val FIXED: ScalesSharing = ScalesSharing("fixed")
        public val FREE: ScalesSharing = ScalesSharing("free")
        public val FREE_X: ScalesSharing = ScalesSharing("free_x")
        public val FREE_Y: ScalesSharing = ScalesSharing("free_y")
    }
}

public data class FacetGridFeature constructor(
    val x: String?,
    val y: String?,
    val scalesSharing: ScalesSharing?,
    val xOrder: OrderDirection,
    val yOrder: OrderDirection,
    val xFormat: String?,
    val yFormat: String?
) : PlotFeature {
    override val featureName: FeatureName = FEATURE_NAME

    public companion object {
        public val FEATURE_NAME: FeatureName = FeatureName("FACET_GRID_FEATURE")
    }

}

public data class FacetWrapFeature constructor(
    val facets: List<String>,
    var nCol: Int?,
    var nRow: Int?,
    var orders: List<OrderDirection>,
    val scalesSharing: ScalesSharing,
    val direction: Direction,
    val formats: List<String?>,
) : PlotFeature {
    override val featureName: FeatureName = FEATURE_NAME

    public companion object {
        public val FEATURE_NAME: FeatureName = FeatureName("FACET_WRAP_FEATURE")
    }

}


/**
 * Splits data by one or two faceting variables. For each data subset creates
 * a plot panel and lays out panels as grid.
 * The grid columns are defined by X faceting variable
 * and rows are defined by Y faceting variable.
 *
 * ```
 * facetGrid(
 *    source<String>("type"),
 *    source<Int>("number of hands"),
 *    yOrder = OrderDirection.DESCENDING
 * )
 * ```
 * TODO params
 * @see org.jetbrains.letsPlot.facet.facetGrid
 */
public fun PlotContext.facetGridX(
    x: DataSource<*>,
    scalesSharing: ScalesSharing? = null,
    order: OrderDirection = OrderDirection.ASCENDING,
    format: String? = null
) {
    features[FacetGridFeature.FEATURE_NAME] =
        FacetGridFeature(
            x.id, null, scalesSharing, order, OrderDirection.ASCENDING, format, null
        )
}

public fun PlotContext.facetGridY(
    y: DataSource<*>,
    scalesSharing: ScalesSharing? = null,
    order: OrderDirection = OrderDirection.ASCENDING,
    format: String? = null
) {
    features[FacetGridFeature.FEATURE_NAME] =
        FacetGridFeature(null, y.id, scalesSharing, OrderDirection.ASCENDING, order, null, format)
}

public fun PlotContext.facetGrid(
    x: DataSource<*>,
    y: DataSource<*>,
    scalesSharing: ScalesSharing? = null,
    xOrder: OrderDirection = OrderDirection.ASCENDING,
    yOrder: OrderDirection = OrderDirection.ASCENDING,
    xFormat: String? = null,
    yFormat: String? = null
) {
    features[FacetGridFeature.FEATURE_NAME] =
        FacetGridFeature(x.id, y.id, scalesSharing, xOrder, yOrder, xFormat, yFormat)
}

@PlotDslMarker
public class FacetWrapContext @PublishedApi internal constructor() {
    private val facets = mutableListOf<DataSource<*>>()
    private val orders = mutableListOf<OrderDirection>()
    private val formats = mutableListOf<String?>()

    public fun facet(source: DataSource<*>, order: OrderDirection = OrderDirection.ASCENDING, format: String? = null) {
        facets.add(source)
        orders.add(order)
        formats.add(format)
    }

    internal fun toFeature(
        nCol: Int? = null,
        nRow: Int? = null,
        scalesSharing: ScalesSharing = ScalesSharing.FIXED,
        direction: Direction = Direction.HORIZONTAL,
    ) =
        FacetWrapFeature(
            facets.map { it.id },
            nCol,
            nRow,
            orders,
            scalesSharing,
            direction,
            formats
        )
}

/**
 * Splits data by one or more faceting variables.
 * For each data subset creates a plot panel and lays out panels according to the `nCol`, `nRow` and `direction` settings.
 *
 * TODO params
 * @see org.jetbrains.letsPlot.facet.facetWrap
 */
public fun PlotContext.facetWrap(
    nCol: Int? = null,
    nRow: Int? = null,
    scalesSharing: ScalesSharing = ScalesSharing.FIXED,
    direction: Direction = Direction.HORIZONTAL,
    block: FacetWrapContext.() -> Unit
) {
    features[FacetWrapFeature.FEATURE_NAME] =
        FacetWrapContext().apply(block).toFeature(nCol, nRow, scalesSharing, direction)
}
