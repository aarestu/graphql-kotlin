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

package com.expediagroup.graphql.sample.accounts.model

import com.expediagroup.graphql.federation.directives.FieldSet
import com.expediagroup.graphql.federation.directives.KeyDirective
import com.expediagroup.graphql.federation.execution.FederatedTypeResolver

@KeyDirective(fields = FieldSet("id username"))
class User(
    val id: String,
    val name: String,
    val username: String
)

class InvalidAccountIdException : RuntimeException()

val userResolver = object : FederatedTypeResolver<User> {

    override suspend fun resolve(representations: List<Map<String, Any>>): List<User?> = representations.map {
        val id = it["id"]?.toString()?.toInt() ?: throw InvalidAccountIdException()
        val username = it["username"]

        users.get(id) ?: throw InvalidAccountIdException()
    }
}

val users = mapOf<Int, User>(
    1 to User("1", "Ada Lovelace", "@ada"),
    2 to User("2", "Alan Turin", "@complete")
)
