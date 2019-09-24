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

package com.expediagroup.graphql.sample.query

import com.expediagroup.graphql.spring.exception.SimpleKotlinGraphQLError
import com.expediagroup.graphql.spring.operations.Query
import graphql.execution.DataFetcherResult
import graphql.execution.ExecutionPath
import graphql.language.SourceLocation
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DataAndErrors : Query {

    fun returnDataAndErrors(): DataFetcherResult<String> {
        val error = SimpleKotlinGraphQLError(RuntimeException(), listOf(SourceLocation(1, 1)), ExecutionPath.rootPath().toList())
        return DataFetcherResult.newResult<String>()
            .data("Hello from data fetcher")
            .error(error)
            .build()
    }

    fun completableFutureDataAndErrors(): CompletableFuture<DataFetcherResult<String>> {
        val error = SimpleKotlinGraphQLError(RuntimeException(), listOf(SourceLocation(1, 1)), ExecutionPath.rootPath().toList())
        val dataFetcherResult = DataFetcherResult.newResult<String>()
            .data("Hello from data fetcher")
            .error(error)
            .build()

        return CompletableFuture.completedFuture(dataFetcherResult)
    }
}
