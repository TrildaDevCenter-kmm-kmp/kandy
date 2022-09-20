package org.jetbrains.kotlinx.ggdsl.echarts

import org.jetbrains.kotlinx.ggdsl.dsl.BindingContext
import org.jetbrains.kotlinx.ggdsl.dsl.PlotContext
import org.jetbrains.kotlinx.ggdsl.echarts.util.linetype.LineType
import org.jetbrains.kotlinx.ggdsl.echarts.util.symbol.Symbol
import org.jetbrains.kotlinx.ggdsl.ir.aes.AesName
import org.jetbrains.kotlinx.ggdsl.ir.aes.MappableNonPositionalAes
import org.jetbrains.kotlinx.ggdsl.ir.aes.NonPositionalAes
import org.jetbrains.kotlinx.ggdsl.ir.aes.ScalablePositionalAes
import org.jetbrains.kotlinx.ggdsl.util.color.Color

public val X: AesName = AesName("x")
public data class XAes(override val context: BindingContext): ScalablePositionalAes {
    override val name: AesName = X
}

public val Y: AesName = AesName("y")
public data class YAes(override val context: BindingContext): ScalablePositionalAes {
    override val name: AesName = Y
}

public val PlotContext.x: XAes
    get() = XAes(this)

public val PlotContext.y: YAes
    get() = YAes(this)


public val SIZE: AesName = AesName("size")

public data class SizeAes(override val context: BindingContext) : MappableNonPositionalAes<Double> {
    override val name: AesName = SIZE
}

public val COLOR: AesName = AesName("color")

public data class ColorAes(override val context: BindingContext) : MappableNonPositionalAes<Color> {
    override val name: AesName = COLOR
}

public val ALPHA: AesName = AesName("alpha")

public data class AlphaAes(override val context: BindingContext) : MappableNonPositionalAes<Double> {
    override val name: AesName = ALPHA
}

public val BORDER_SIZE: AesName = AesName("border_size")

public data class BorderWidthAes(override val context: BindingContext) : NonPositionalAes<Double> {
    override val name: AesName = BORDER_SIZE
}

public val BORDER_COLOR: AesName = AesName("border_color")

public data class BorderColorAes(override val context: BindingContext) : NonPositionalAes<Color> {
    override val name: AesName = BORDER_COLOR
}

public val WIDTH: AesName = AesName("width")

public data class WidthAes(override val context: BindingContext) : NonPositionalAes<Double> {
    override val name: AesName = WIDTH
}

public val SYMBOL: AesName = AesName("symbol")

public data class SymbolAes(override val context: BindingContext) : MappableNonPositionalAes<Symbol> {
    override val name: AesName = SYMBOL
}

public val LINE_TYPE: AesName = AesName("line_type")

public data class LineTypeAes(override val context: BindingContext) : NonPositionalAes<LineType> {
    override val name: AesName = LINE_TYPE
}
