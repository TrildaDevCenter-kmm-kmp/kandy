package org.jetbrains.kotlinx.kandy.letsplot.layers.context

import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.toColumn
import org.jetbrains.kotlinx.kandy.dsl.internal.DataFramePlotContext
import org.jetbrains.kotlinx.kandy.ir.bindings.NonPositionalMapping
import org.jetbrains.kotlinx.kandy.ir.bindings.NonPositionalSetting
import org.jetbrains.kotlinx.kandy.letsplot.internal.ALPHA
import org.jetbrains.kotlinx.kandy.letsplot.internal.FILL
import org.jetbrains.kotlinx.kandy.letsplot.internal.X_MAX
import org.jetbrains.kotlinx.kandy.letsplot.internal.X_MIN
import org.jetbrains.kotlinx.kandy.letsplot.internal.Y_MAX
import org.jetbrains.kotlinx.kandy.letsplot.internal.Y_MIN
import org.jetbrains.kotlinx.kandy.letsplot.layers.geom.RECT
import org.jetbrains.kotlinx.kandy.util.color.Color
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RectTests {
    private val xMin = listOf(0.1).toColumn("xMin")
    private val xMax = listOf(2.1).toColumn("xMax")
    private val yMin = listOf(1).toColumn("yMin")
    private val yMax = listOf(5).toColumn("yMax")
    private val color = listOf("blue").toColumn("color")
    private val alpha = listOf(0.5).toColumn("alpha")

    private val df = dataFrameOf(xMin, xMax, yMin, yMax, color, alpha)

    private val parentContext = DataFramePlotContext(df)
    private lateinit var context: RectContext

    @BeforeTest
    fun setUp() {
        context = RectContext(parentContext)
    }

    @Test
    fun `geom is RECT`() {
        assertEquals(RECT, context.geom)
    }

    @Test
    fun `requiredAes contains X_MIN, X_MAX, Y_MIN, Y_MAX`() {
        assertTrue(context.requiredAes.contains(X_MIN))
        assertTrue(context.requiredAes.contains(X_MAX))
        assertTrue(context.requiredAes.contains(Y_MIN))
        assertTrue(context.requiredAes.contains(Y_MAX))
    }

    @Test
    fun `fillColor const for pointRange`() {
        context.fillColor = Color.BLUE
        assertEquals(Color.BLUE, (context.bindingCollector.settings[FILL] as NonPositionalSetting<*>).value)
    }

    @Test
    fun `fillColor str mapping for pointRange`() {
        context.fillColor("color")
        assertEquals("color", (context.bindingCollector.mappings[FILL] as NonPositionalMapping<*, *>).columnID)
    }

    @Test
    fun `fillColor dataColumn mapping for pointRange`() {
        context.fillColor(color)
        assertEquals("color", (context.bindingCollector.mappings[FILL] as NonPositionalMapping<*, *>).columnID)
    }

    @Test
    fun `fillColor iterable mapping for pointRange`() {
        context.fillColor(listOf("red"))
        assertEquals("fill", (context.bindingCollector.mappings[FILL] as NonPositionalMapping<*, *>).columnID)
    }

    @Test
    fun `alpha const for pointRange`() {
        context.alpha = 0.1
        assertEquals(0.1, (context.bindingCollector.settings[ALPHA] as NonPositionalSetting<*>).value)
    }

    @Test
    fun `alpha str mapping for pointRange`() {
        context.alpha("alpha")
        assertEquals("alpha", (context.bindingCollector.mappings[ALPHA] as NonPositionalMapping<*, *>).columnID)
    }

    @Test
    fun `alpha dataColumn mapping for pointRange`() {
        context.alpha(alpha)
        assertEquals("alpha", (context.bindingCollector.mappings[ALPHA] as NonPositionalMapping<*, *>).columnID)
    }

    @Test
    fun `alpha iterable mapping for pointRange`() {
        context.alpha(listOf(0.2, 0.5, .1))
        assertEquals("alpha", (context.bindingCollector.mappings[ALPHA] as NonPositionalMapping<*, *>).columnID)
    }

    @Test
    fun `xMin for rect`() {
        context.xMin(xMin)
        assertEquals(X_MIN, context.bindingCollector.mappings[X_MIN]?.aes)
        assertEquals("xMin", context.bindingCollector.mappings[X_MIN]?.columnID)
    }

    @Test
    fun `xMax for rect`() {
        context.xMax(xMax)
        assertEquals(X_MAX, context.bindingCollector.mappings[X_MAX]?.aes)
        assertEquals("xMax", context.bindingCollector.mappings[X_MAX]?.columnID)
    }

    @Test
    fun `yMin for lineRange`() {
        context.yMin(yMin)
        assertEquals(Y_MIN, context.bindingCollector.mappings[Y_MIN]?.aes)
        assertEquals("yMin", context.bindingCollector.mappings[Y_MIN]?.columnID)
    }

    @Test
    fun `yMax for lineRange`() {
        context.yMax(yMax)
        assertEquals(Y_MAX, context.bindingCollector.mappings[Y_MAX]?.aes)
        assertEquals("yMax", context.bindingCollector.mappings[Y_MAX]?.columnID)
    }
}
