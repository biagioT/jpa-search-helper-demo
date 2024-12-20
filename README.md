Spring Boot demo project for [JPA Search Helper](https://github.com/biagioT/jpa-search-helper/tree/main)


Start the application and try it:
```bash
./gradlew bootRun
```

- OpenAPI/Swagger: http://localhost:8080/swagger-ui/index.html
- GraphiQL: http://localhost:8080/graphiql
- Grafana: http://localhost:3000
  - Spring Boot dashboard: http://localhost:3000/dashboards
  - Prometheus & Tempo: http://localhost:3000/explore

### Examples:
Note: the search requires pagination: it is mandatory to set _pageSize_ or __limit_ depending on the endpoint.

**Mode 1**

```bash
curl --location 'http://localhost:8080/books?copiesSold_lte=500&_limit=10'
```

or 

```bash
curl --location 'http://localhost:8080/books?_limit=10&authors.name_contains=John'
```

**Mode 2**
```bash
curl --location 'http://localhost:8080/books' \
--header 'Content-Type: application/json' \
--data '{
    "filter": {
        "operator": "or",
        "filters": [
            {
                "operator": "eq",
                "value": "1034567890123456",
                "key": "isbn"
            },
            {
                "operator": "in",
                "values": [
                    "1034567890123456",
                    "1234567890123456"
                ],
                "key": "isbn"
            },
            {
                "operator": "and",
                "filters": [
                    {
                        "operator": "null",
                        "key": "title",
                        "options": {
                            "negate": false
                        }
                    },
                    {
                        "operator": "lt",
                        "key": "pages",
                        "value": 10
                    }
                ]
            }
        ]
    },
    "options": {
        "pageSize": 4
    }
}'
```

**Projection**

GraphQL query on http://localhost:8080/graphql

```graphql
query {

    projection(filters : [

      { key: "isbn_in", value: "1234567890123456,2234567890123456" }

    ]) {

        isbn
        authors {
            name
            genres {
                description
            }
        }

    }

}
```