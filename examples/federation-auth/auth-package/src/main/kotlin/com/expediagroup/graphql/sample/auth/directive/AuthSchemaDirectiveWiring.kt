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

package com.expediagroup.graphql.sample.auth.directive

import com.expediagroup.graphql.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.directives.KotlinSchemaDirectiveWiring
import com.expediagroup.graphql.sample.auth.context.UserGraphQLContext
import com.expediagroup.graphql.sample.auth.exceptions.UnauthorizedException
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition

class AuthSchemaDirectiveWiring : KotlinSchemaDirectiveWiring {
    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val field = environment.element
        val targetAuthRole = environment.directive.getArgument("roles").value
        val originalDataFetcher: DataFetcher<Any> = environment.getDataFetcher()

        val authorisationFetcherFetcher = DataFetcher<Any> { dataEnv ->

            val user = dataEnv.getContext<UserGraphQLContext>().user ?: throw UnauthorizedException()
            if (targetAuthRole != "all" && user.roles != targetAuthRole) {
                throw UnauthorizedException()
            }

            originalDataFetcher.get(dataEnv)
        }
        environment.setDataFetcher(authorisationFetcherFetcher)
        return field
    }
}
