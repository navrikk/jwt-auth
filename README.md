# jwt-auth

Sample service for JWT authentication & authorisation

## Build & Test locally
* Needs docker
* Needs no local test database
* `mvn clean package`

## Run locally
* Run postgres database on docker 
```
docker rm -f postgres_collection ; docker run --rm -d --name postgres_collection -e POSTGRES_DB=collection -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 postgres:11.6
```
* Run via IDE or commandline with following `VM Options`
```
-Dspring.profiles.active=local
```
* A user of role manager with phone number `5555512345` will be seeded via `DevelopmentDataLoader`
