# FamilyCookBook
A library service for cooking recipes with options for sharing

## Motivation
There are many online recipe sites, but there isn't a good way to bookmark, store and share recipes with friends or family. This project aims to be a library of favorite recipes, with a small number of users which know each other. A place to share family recipes without ads or long blogposts forcing content on users.
Another aspect of this project is to try out technology. This library is self-hosted "on-premise" and with an api to connect database backend with a webserver front, a mobile app or with other (commercial) services.

## Architecture
The architecture is mostly split in 3 parts, possibly 4 if I find a willing app-developer...

### Backend
The backend consists of a database and a (mostly) autogenerated GraphQL-API paired with web endpoints for maintenance, file im-/exports and simple admin functionality.
- ToDos: 
  - which database to use, currently it's an H2 in-memory-db. But this is only usable for development. Since dbs are mostly hidden behind spring data and JPA, it shouldn't matter that much for this application
  - auto generation and then customization of api-provider from graphql files.

### API-commons
This module contains the `*.graphqls` files which define the api between backend and frontend.

From these files, java code is generated using maven plugin `name`.

### Frontend/Webserver
Using Spring boot this module provides a webserver which connects to the backend using GraphQL. Webpages are rendered server-side using Apache Wicket (Simply because I want to avoid javascript as much as possible 😂)

## Contribute
I would love other people helping me code this. Just write me a message here on github.

### Future ideas
Using `Cookmate` for importing recipes from various online sources, then exporting them using their xml export and importing them here. See their xsd, sourced from [here](https://blog.mycookbook-online.net/my-cookbook-xml-schema/).