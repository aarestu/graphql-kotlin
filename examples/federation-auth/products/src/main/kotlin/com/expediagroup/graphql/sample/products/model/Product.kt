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

package com.expediagroup.graphql.sample.products.model

import com.expediagroup.graphql.federation.directives.FieldSet
import com.expediagroup.graphql.federation.directives.KeyDirective
import com.expediagroup.graphql.federation.execution.FederatedTypeResolver

@KeyDirective(fields = FieldSet("upc"))
class Product(
    val upc: Int,
    val name: String,
    val price: Int,
    val weight: Int
)

class InvalidProductUpcException : RuntimeException()

val productResolver = object : FederatedTypeResolver<Product> {

    override suspend fun resolve(representations: List<Map<String, Any>>): List<Product?> = representations.map {
        val id = it["upc"]?.toString()?.toIntOrNull() ?: throw InvalidProductUpcException()

        products.get(id) ?: throw InvalidProductUpcException()
    }
}

val products = mapOf<Int, Product>(
    1 to Product(1, "Table", 899, 100),
    2 to Product(2, "Couch", 1299, 1000),
    3 to Product(3, "Chair", 54, 50)
)
