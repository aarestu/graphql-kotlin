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
const {ApolloServer} = require("apollo-server");
const {ApolloGateway, RemoteGraphQLDataSource} = require("@apollo/gateway");

const gateway = new ApolloGateway({
    serviceList: [
        {name: "accounts", url: "http://localhost:8080/graphql"},
        {name: "products", url: "http://localhost:8081/graphql"},
        {name: "reviews", url: "http://localhost:8082/graphql"},
    ],
    buildService({name, url}) {
        return new RemoteGraphQLDataSource({
            url,
            willSendRequest({request, context}) {
                // pass the user's token from the context to underlying services
                if (context.token) {
                    request.http.headers.set('authorization', context.token);
                }
            },
        });
    }
});

(async () => {
    const {schema, executor} = await gateway.load();

    const server = new ApolloServer({
        schema,
        executor,
        context: async ({req}) => {
            // get the user token from the headers
            const token = req.headers.authorization || null;

            // add the token to the context
            return {token};
        },
    });

    server.listen().then(({url}) => {
        console.log(`🚀 Server ready at ${url}`);
    });
})();
