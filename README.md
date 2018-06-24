# Skip API


## User Stories

A restaurant have clients that want listen to music during the lunch/dinner, the restaurant has an audio system, currently, the customers should write in a paper in order to do the playlist. The goal of this project is to eliminate the manual effort and making available an API to manage the playlist.

## Getting started 

The API provides a connection to the Spotify API, in order to return the track names, a Spotify App was created in order to search the songs names. 
Also was created a Mysql database, that store the playlist selected by the customers, for this scenario also is available an endpoint to do create a playlist.
The Project runs on java 8,  using Spark Framework and intentionally runs on AWS RDS (MySQL) and AWS ECS. 


## Curl usage 

> /retriveSongs/:song

This GET initiate the search process, This endpoint shall return a JSON object with information about the first 10 tracks returned by the Spotify API, which will be used further to create a playlist.

    curl -H "Content-Type: application/json" -X GET http://localhost:8080/retriveSongs/acdc
    
    {...}
    
> /storeListSongs/:id>/:trackName

This POST initiates a process to store a track in the database, the request shall return a success message in case of success.

    curl -H "Content-Type: application/json" -X POST http://localhost:8080/storeListSongs/6mUdeDZCsExyJLMdAfDuwh/Back In Black
    
    "success"


> /getSongs

This GET retrieve the playlist, this request connect directly to the database in order to retrieve the playlist.

    curl -H "Content-Type: application/json" -X GET http://localhost:8080/getSongs
    
    {"6mUdeDZCsExyJLMdAfDuwh":"Back In Black", ...}


> /removeSong

This endpoint is responsible to remove the oldest track in the playlist, that's mean, the track was played.

    curl -H "Content-Type: application/json" -X GET http://localhost:8080/removeSong
    
    "success"
    
## Architecture

The image below explains the architecture of this project, the API shall run in an AWS instance, that connect in an AWS RDS instance.
The web app, in which will display the data, connect directly in the API.

![alt text](https://github.com/guilhermemalfatti/skip-api/blob/feat/readme/img/design.png)

