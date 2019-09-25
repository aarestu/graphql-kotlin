/*
 * Copyright 2019 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expediagroup.graphql.spring.exception

import graphql.execution.DataFetcherExceptionHandlerParameters
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KotlinDataFetcherExceptionHandlerTest {

    @Test
    fun `test exception handler`() {

        val parameters: DataFetcherExceptionHandlerParameters = mockk {
            every { exception } returns Throwable()
            every { sourceLocation } returns mockk()
            every { path } returns mockk {
                every { toList() } returns listOf("foo")
            }
        }

        val handler = KotlinDataFetcherExceptionHandler()
        val result = handler.onException(parameters)

        assertNotNull(result.errors)
        assertEquals(expected = 1, actual = result.errors.size)
    }
}
