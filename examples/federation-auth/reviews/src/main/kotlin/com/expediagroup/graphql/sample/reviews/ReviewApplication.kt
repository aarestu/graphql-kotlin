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

package com.expediagroup.graphql.sample.reviews

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.federation.FederatedSchemaGeneratorConfig
import com.expediagroup.graphql.federation.execution.FederatedTypeRegistry
import com.expediagroup.graphql.sample.reviews.directive.CustomDirectiveWiringFactory
import com.expediagroup.graphql.sample.reviews.extension.CustomFederatedSchemaGeneratorHooks
import com.expediagroup.graphql.sample.reviews.model.productResolver
import com.expediagroup.graphql.sample.reviews.model.userResolver
import com.expediagroup.graphql.spring.GraphQLConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.expediagroup.graphql.sample")
class ReviewApplication {

    @Bean
    fun federatedTypeRegistry() = FederatedTypeRegistry(
        mapOf(
            "Product" to productResolver,
            "User" to userResolver
        )
    )

    @Bean
    fun wiringFactory() = CustomDirectiveWiringFactory()

    @Bean
    fun federatedSchemaConfig(config: GraphQLConfigurationProperties, registry: FederatedTypeRegistry, wiringFactory: KotlinDirectiveWiringFactory): FederatedSchemaGeneratorConfig = FederatedSchemaGeneratorConfig(
        supportedPackages = config.packages,
        hooks = CustomFederatedSchemaGeneratorHooks(registry, wiringFactory)
    )
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<ReviewApplication>(*args)
}
