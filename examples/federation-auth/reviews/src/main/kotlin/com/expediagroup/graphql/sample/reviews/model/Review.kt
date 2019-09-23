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

package com.expediagroup.graphql.sample.reviews.model

import com.expediagroup.graphql.federation.directives.FieldSet
import com.expediagroup.graphql.federation.directives.KeyDirective
import com.expediagroup.graphql.federation.directives.ProvidesDirective

@KeyDirective(fields = FieldSet("id"))
class Review(
    val id: String,
    val body: String,
    @property:ProvidesDirective(fields = FieldSet("username")) val author: User,
    val product: Product
)

val reviews = mapOf<Int, Review>(
    1 to Review("1", "Love it!", User("1", ""), Product(1)),
    2 to Review("2", "Love it!", User("1", ""), Product(2)),
    3 to Review("3", "Love it!", User("2", ""), Product(3)),
    4 to Review("4", "Love it!", User("4", ""), Product(4))
)
