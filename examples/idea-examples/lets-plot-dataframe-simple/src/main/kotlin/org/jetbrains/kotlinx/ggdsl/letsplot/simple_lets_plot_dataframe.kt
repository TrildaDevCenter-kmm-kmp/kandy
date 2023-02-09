package org.jetbrains.kotlinx.ggdsl.letsplot

//import org.jetbrains.kotlinx.ggdsl.dataframe.create
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.ggdsl.dataframe.plot
import org.jetbrains.kotlinx.ggdsl.dsl.column.invoke
import org.jetbrains.kotlinx.ggdsl.dsl.continuous
import org.jetbrains.kotlinx.ggdsl.dsl.continuousPos
import org.jetbrains.kotlinx.ggdsl.dsl.invoke
import org.jetbrains.kotlinx.ggdsl.dsl.scaled
import org.jetbrains.kotlinx.ggdsl.letsplot.export.save
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.bars
import org.jetbrains.kotlinx.ggdsl.letsplot.layers.line
import org.jetbrains.kotlinx.ggdsl.letsplot.util.linetype.LineType
import org.jetbrains.kotlinx.ggdsl.util.color.Color

public fun main() {

    val simpleDataFrame = dataFrameOf(
        "time" to listOf(0, 1, 2, 4, 5, 7, 8, 9),
        "temperature" to listOf(12.0, 14.2, 15.1, 15.9, 17.9, 15.6, 14.2, 24.3),
        "humidity" to listOf(0.5, 0.32, 0.11, 0.89, 0.68, 0.57, 0.56, 0.5)
    )

    val simplePlot = simpleDataFrame.plot {
        x("time"<Int>())

        y("temperature"<Double>().scaled(
            continuousPos(0.0 to 25.5)
        ))

        bars {
            color("humidity"<Double>().scaled(continuous(
                rangeLimits = Color.YELLOW to Color.RED
            )))
            borderLine.width(0.0)
        }

        line {
            width(3.0)
            color(Color.hex("#6e5596"))
            type(LineType.DOTDASH)
        }

        layout {
            title = "Simple plot with lets-plot"
            caption = "See `examples` section for more\n complicated and interesting examples!"
        }


    }

    simplePlot.save("lets_plot_dataframe_simple.png")
}
