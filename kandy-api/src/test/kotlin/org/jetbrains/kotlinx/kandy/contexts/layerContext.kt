/*
* Copyright 2020-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
*/

package org.jetbrains.kotlinx.kandy.contexts

import io.mockk.every
import io.mockk.mockk
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.kandy.dsl.internal.DatasetHandler
import org.jetbrains.kotlinx.kandy.dsl.internal.LayerCollectorContext
import org.jetbrains.kotlinx.kandy.dsl.internal.LayerContext
import org.jetbrains.kotlinx.kandy.ir.aes.AesName
import org.jetbrains.kotlinx.kandy.ir.bindings.NonPositionalMapping
import org.jetbrains.kotlinx.kandy.ir.bindings.NonPositionalMappingParameters
import org.jetbrains.kotlinx.kandy.ir.bindings.PositionalMapping
import org.jetbrains.kotlinx.kandy.ir.bindings.PositionalMappingParameters
import org.jetbrains.kotlinx.kandy.ir.data.NamedData
import org.jetbrains.kotlinx.kandy.ir.geom.Geom
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LayerContextTest {

    private lateinit var layerContext: LayerContext
    private lateinit var parentContext: LayerCollectorContext

    private val namedData = NamedData(DataFrame.Empty)
    private val dataHandler = DatasetHandler(namedData)
    private val aesName = mockk<AesName>()
    private val name = "columnName"
    private val positionalParameters = mockk<PositionalMappingParameters<Any>>()
    private val nonPositionalParameters = mockk<NonPositionalMappingParameters<Any, Any>>()
    private val columnID = "columnId"

    @BeforeTest
    fun setup() {
        parentContext = mockk<LayerCollectorContext> {
            every { datasetIndex } returns 0
            every { plotContext } returns mockk(relaxed = true) {
                every { datasetHandlers } returns mutableListOf(dataHandler)
            }
        }

        layerContext = object : LayerContext(parentContext) {
            override val geom: Geom = mockk()
            override val requiredAes: Set<AesName> = mockk()
            override val datasetHandler: DatasetHandler = mockk(relaxed = true)
        }
    }

    @Test
    fun `test addNonPositionalMapping with columnID`() {
        every { layerContext.datasetHandler.takeColumn(columnID) } returns columnID

        val result = layerContext.addNonPositionalMapping(aesName, columnID, nonPositionalParameters)
        val expectedMapping = NonPositionalMapping(aesName, columnID, nonPositionalParameters)

        assertEquals(expectedMapping, result)
    }

    @Test
    fun `test addNonPositionalMapping with DataColumn and overrideDataset`() {
        val dataColumn = mockk<DataColumn<Any>> {
            every { size() } returns 3
        }

        every { layerContext.datasetHandler.addColumn(dataColumn) } returns columnID

        val result = layerContext.addNonPositionalMapping(aesName, dataColumn, nonPositionalParameters)
        val expectedMapping = NonPositionalMapping(aesName, columnID, nonPositionalParameters)

        assertEquals(1, layerContext.datasetIndex)
        assertEquals(expectedMapping, result)
    }

    @Test
    fun `test addNonPositionalMapping with list of values and overrideDataset`() {
        val values = listOf<Any>("test1", "test2")
        every { layerContext.datasetHandler.addColumn(values, name) } returns columnID

        val result = layerContext.addNonPositionalMapping(aesName, values, name, nonPositionalParameters)
        val expectedMapping = NonPositionalMapping(aesName, columnID, nonPositionalParameters)

        assertEquals(1, layerContext.datasetIndex)
        assertEquals(expectedMapping, result)
    }

    @Test
    fun `test addPositionalMapping with columnID`() {
        every { layerContext.datasetHandler.takeColumn(columnID) } returns columnID

        val result = layerContext.addPositionalMapping(aesName, columnID, positionalParameters)
        val expectedMapping = PositionalMapping(aesName, columnID, positionalParameters)

        assertEquals(expectedMapping, result)
    }

    @Test
    fun `test addPositionalMapping with DataColumn and overrideDataset`() {
        val dataColumn = mockk<DataColumn<Any>> {
            every { size() } returns 3
        }

        every { layerContext.datasetHandler.addColumn(dataColumn) } returns columnID

        val result = layerContext.addPositionalMapping(aesName, dataColumn, positionalParameters)
        val expectedMapping = PositionalMapping(aesName, columnID, positionalParameters)

        assertEquals(1, layerContext.datasetIndex)
        assertEquals(expectedMapping, result)
    }

    @Test
    fun `test addPositionalMapping with list of values and overrideDataset`() {
        val values = listOf<Any>("test1", "test2")
        every { layerContext.datasetHandler.addColumn(values, name) } returns columnID

        val result = layerContext.addPositionalMapping(aesName, values, name, positionalParameters)
        val expectedMapping = PositionalMapping(aesName, columnID, positionalParameters)

        assertEquals(1, layerContext.datasetIndex)
        assertEquals(expectedMapping, result)
    }
}