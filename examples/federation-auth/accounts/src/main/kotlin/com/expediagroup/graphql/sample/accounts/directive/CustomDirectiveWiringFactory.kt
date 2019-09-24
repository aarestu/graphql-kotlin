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

package com.expediagroup.graphql.sample.accounts.directive

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.directives.KotlinSchemaDirectiveEnvironment
import com.expediagroup.graphql.directives.KotlinSchemaDirectiveWiring
import com.expediagroup.graphql.sample.auth.directive.AuthSchemaDirectiveWiring
import com.expediagroup.graphql.sample.auth.directive.Auth
import com.google.common.base.CaseFormat
import graphql.schema.GraphQLDirectiveContainer
import kotlin.reflect.KClass

class CustomDirectiveWiringFactory : KotlinDirectiveWiringFactory() {

    private val authSchemaDirectiveWiring = AuthSchemaDirectiveWiring()
    private val upperSchemaDirectiveWiring = UpperSchemaDirectiveWiring()

    override fun getSchemaDirectiveWiring(environment: KotlinSchemaDirectiveEnvironment<GraphQLDirectiveContainer>): KotlinSchemaDirectiveWiring? = when {
        environment.directive.name == getDirectiveName(Auth::class) -> authSchemaDirectiveWiring
        environment.directive.name == getDirectiveName(Upper::class) -> upperSchemaDirectiveWiring
        else -> null
    }
}


internal fun getDirectiveName(kClass: KClass<out Annotation>): String =
    CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, kClass.simpleName!!)
