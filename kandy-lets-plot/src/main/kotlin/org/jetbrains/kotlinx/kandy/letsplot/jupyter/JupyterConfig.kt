package org.jetbrains.kotlinx.kandy.letsplot.jupyter

import org.jetbrains.kotlinx.kandy.util.context.SelfInvocationContext

public class JupyterConfig: SelfInvocationContext {
    public var applyColorScheme: Boolean = true
    public var swingEnabled: Boolean = true
}
