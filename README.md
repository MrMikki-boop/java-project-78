### Hexlet tests and linter status:
[![Actions Status](https://github.com/MrMikki-boop/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/MrMikki-boop/java-project-78/actions)
[![Java CI](https://github.com/MrMikki-boop/java-project-78/actions/workflows/main.yml/badge.svg)](https://github.com/MrMikki-boop/java-project-78/actions/workflows/main.yml)

[![Maintainability](https://api.codeclimate.com/v1/badges/172cc8c3b415d9db6f27/maintainability)](https://codeclimate.com/github/MrMikki-boop/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/172cc8c3b415d9db6f27/test_coverage)](https://codeclimate.com/github/MrMikki-boop/java-project-78/test_coverage)

# Data Validator
**Data validator** is a library that can be used to check the correctness of any data. There are many such libraries in every language, since almost all programs work with external data that needs to be checked for correctness. First of all, we are talking about the data of forms filled out by users.
```java
var validator = new Validator();
```

### About the project:
It represents a simple schema builder for object validation. Three schemas are available: `StringSchema`, `NumberSchema` and `MapSchema`. They can check if the object isn't empty but also have different features according to the type of an object to validate. You can use all methods in the scheme at once or separately.

## StringSchema

This schema validate String objects. To choose this schema write:

```java
var schema = validator.string();
```

StringSchema has three validation methods:
* __required()__ - makes the fields required and limits the possibility to use null or empty String.
* __minLength()__ - adds a minimum length constraint for the String. The String must be equal or longer than a specified number. Requires an integer parameter of minimum length.
* __contains()__ - adds a String content constraint. The String must contain a substring passed in the method parameter.

-----
## NumberSchema

This schema validate Integer objects. To choose this schema write:

```java
var schema = validator.number();
```

NumberSchema has three validation methods:
* __required()__ - makes the fileds required and limits the possibility to use null.
* __positive()__ - adds a constraint to use negative numbers.
* __range()__ - adds a range constraint (inclusive). Requires two integer parameters of the first and the last numbers of range.

-----
## MapSchema

This schema validate Map objects. To schoose this schema write:

```java
var schema = validator.map();
```

MapSchema has three validation methods:
* __required()__ - makes the fields required and limits the possibility to use null.
* __sizeOf()__ - adds a map size constraint. The K-V count must be equal to the number passed in the method parameter.
* __shape()__ - adds constraints to map values. Accepts as a parameter a map of keys whose values need to be validated and schemas that would validate the values.

Below is an example of how shape() method works:

```java
Map<String, BaseSchema> schemas = new HashMap<>();
schemas.put("name", validator.string().required());
schemas.put("age", validator.number().positive());
schema.shape(schemas);

Map<String, Object> map = new HashMap<>();
map.put("name", "Maksim");
map.put("age", 19);
schema.isValid(map); // true

map.put("name", 987);
schema.isValid(map); // false
```
## Run
### Setup
```sh
make build
```
### Start
```sh
make run-dist
```
### Update versions plugin
```sh
make last
```
### Linter
```sh
make lint
```