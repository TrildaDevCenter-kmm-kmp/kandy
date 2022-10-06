package org.jetbrains.kotlinx.ggdsl.letsplot.stat.bin

import org.jetbrains.kotlinx.ggdsl.letsplot.stat.Statistic

public sealed interface BinStatistic<T>: Statistic<T> {
    // todo type
    public object Bins: BinStatistic<Double> {
        public val NAME: String = "..x.."
        override val name: String = NAME

    }
    public object Count: BinStatistic<Int> {
        public val NAME: String = "..count.."
        override val name: String = NAME

    }
    public object Density: BinStatistic<Double> {
        public val NAME: String = "..density.."
        override val name: String = NAME

    }
}