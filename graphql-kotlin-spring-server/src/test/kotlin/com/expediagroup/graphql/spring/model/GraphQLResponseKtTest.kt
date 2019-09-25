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

package com.expediagroup.graphql.spring.model

import graphql.ExecutionResult
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GraphQLResponseKtTest {

    @Test
    fun `null data, errors, and extenstions can still be mapped`() {
        val executionResult: ExecutionResult = mockk {
            every { getData<Any>() } returns null
            every { errors } returns null
            every { extensions } returns null
        }

        val result = executionResult.toGraphQLResponse()

        assertNull(result.data)
        assertNull(result.errors)
        assertNull(result.extensions)
    }

    @Test
    fun `null errors or null extensions converts to null`() {
        val executionResult: ExecutionResult = mockk {
            every { getData<Any>() } returns mockk()
            every { errors } returns null
            every { extensions } returns null
        }

        val result = executionResult.toGraphQLResponse()

        assertNotNull(result.data)
        assertNull(result.errors)
        assertNull(result.extensions)
    }

    @Test
    fun `empty list for errors or empty map for extensions converts to null`() {
        val executionResult: ExecutionResult = mockk {
            every { getData<Any>() } returns mockk()
            every { errors } returns emptyList()
            every { extensions } returns emptyMap()
        }

        val result = executionResult.toGraphQLResponse()

        assertNotNull(result.data)
        assertNull(result.errors)
        assertNull(result.extensions)
    }

    @Test
    fun `if errors or extensions is set, the values are copied`() {
        val executionResult: ExecutionResult = mockk {
            every { getData<Any>() } returns mockk()
            every { errors } returns listOf(mockk {
                every { message } returns "hello"
            })
            every { extensions } returns mapOf("foo" to "bar")
        }

        val result = executionResult.toGraphQLResponse()

        assertNotNull(result.data)
        val errors = result.errors
        assertNotNull(errors)
        assertEquals(expected = "hello", actual = errors.firstOrNull()?.message)
        val extensions = result.extensions
        assertNotNull(extensions)
        assertEquals(expected = "bar", actual = extensions["foo"])
    }
}
