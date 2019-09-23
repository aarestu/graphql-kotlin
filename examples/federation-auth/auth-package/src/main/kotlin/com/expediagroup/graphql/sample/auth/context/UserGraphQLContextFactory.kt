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

package com.expediagroup.graphql.sample.auth.context

import com.expediagroup.graphql.sample.auth.AuthConfigurationProperties
import com.expediagroup.graphql.sample.auth.exceptions.InvalidTokenException
import com.expediagroup.graphql.sample.auth.model.CurrentUser
import com.expediagroup.graphql.sample.auth.service.AuthService
import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component

@Component
class UserGraphQLContextFactory(val authService: AuthService, val authConfigurationProperties: AuthConfigurationProperties) : GraphQLContextFactory<UserGraphQLContext> {

    override suspend fun generateContext(request: ServerHttpRequest, response: ServerHttpResponse): UserGraphQLContext {
        val authorizationHeader = request.headers.getFirst(authConfigurationProperties.header) ?: ""

        val currentUser: CurrentUser? = try {
            val token = authorizationHeader.replace(authConfigurationProperties.type, "")
            authService.decode(token.trim())
        } catch (e: InvalidTokenException) {
            null
        }

        return UserGraphQLContext(
            currentUser,
            request,
            response
        )
    }
}
