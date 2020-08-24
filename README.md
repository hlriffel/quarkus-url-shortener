# url-shortener project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

To run the application and all of its services, first you must execute the following: 
```
./mvnw package
```
After that, run the application by executing the `docker-compose.yaml` inside `src/main/docker` using Docker Compose.

## APIs

This project is using both `OpenAPI` and `SwaggerUI`, so the YAML file containing the description of the APIs is available at `localhost:8080/openapi` after the application is served and the Swagger documentation is available at `localhost:8080/docs`.

#### /shorten

This endpoint returns the shortened key of a URL. By specifying this key as the parameter for the next API, you are redirected to the original URL.

##### payload

```json
{
  "url": "A string containing the original URL"
}
```

##### response

```text
key as plain text
```

#### /{key}

If the key exists, this endpoint redirects to the original URL by specifying the shortened key. Otherwise, a 404 response is returned. 