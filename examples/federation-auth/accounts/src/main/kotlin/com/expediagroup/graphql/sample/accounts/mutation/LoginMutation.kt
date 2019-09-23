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

package com.expediagroup.graphql.sample.accounts.mutation

import com.expediagroup.graphql.sample.accounts.model.AuthInfo
import com.expediagroup.graphql.sample.accounts.model.users
import com.expediagroup.graphql.sample.auth.exceptions.InvalidLoginException
import com.expediagroup.graphql.sample.auth.model.CurrentUser
import com.expediagroup.graphql.sample.auth.service.AuthService
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component

@Component
class LoginMutation(val authService: AuthService) : Mutation {

    fun login(username: String, password: String): AuthInfo {
        val user = users.get(1)!!
        if (username.equals(user.username) and password.equals("rahasia")) {
            return AuthInfo(
                authService.encode(CurrentUser("1", "user"))
            )
        }
        throw InvalidLoginException()
    }
}
