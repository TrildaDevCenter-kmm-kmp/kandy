/*
* Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.ggdsl.dsl

import org.jetbrains.kotlinx.ggdsl.ir.data.ColumnPointer
import org.jetbrains.kotlinx.ggdsl.ir.data.NamedDataInterface
import kotlin.reflect.KProperty

/**
 * Returns a new [ColumnPointer].
 *
 * @param T the type of source
 * @param id the name of source in [NamedDataInterface]
 */

public  fun < T : Any> columnPointer(id: String): ColumnPointer<T> =
    ColumnPointer(id)

// TODO
public class UnnamedColumnPointer<T : Any> {
    public operator fun getValue(thisRef: Any?, property: KProperty<*>): ColumnPointer<T> {
        return ColumnPointer(property.name)

    }
}

// todo

public inline operator fun <reified T : Any> String.invoke(): ColumnPointer<T> =
    ColumnPointer(this)

// todo
public inline fun <reified T : Any> columnPointer(): UnnamedColumnPointer<T> =
    UnnamedColumnPointer()

