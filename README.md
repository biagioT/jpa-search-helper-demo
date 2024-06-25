Spring Boot demo project for [JPA Search Helper](https://github.com/biagioT/jpa-search-helper/tree/main)

- OpenAPI/Swagger: http://localhost:8080/swagger-ui/index.html
- Grafana: http://localhost:3000
  - Prometheus: http://localhost:3000/explore (select Prometheus datasource)
  - Tempo: http://localhost:3000/explore (select Tempo datasource)

Note: the search requires pagination: it is mandatory to set _pageSize_ or __limit_ depending on the endpoint.
