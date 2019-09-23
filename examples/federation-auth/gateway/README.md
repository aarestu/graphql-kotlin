# Apollo Federation

This is a simple Apollo Federation Gateway. For more info see the Apollo docs

https://www.apollographql.com/docs/apollo-server/federation/introduction/

## Setup

Install the correct version of Node and have yarn installed locally

```shell script
nvm i
```

```shell script
yarn install
```

Then start the other applications

* `accounts` should run on port 8080
* `products` should run on port 8081
* `reviews` should run on port 8082

Then you can start the gateway
```shell script
yarn start
```
