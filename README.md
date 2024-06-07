# Football Management Application

This project is a Football Management Application built using Java Spring Boot. It allows users to manage football leagues, teams, players, and matches. Users can also randomize and generate match schedules for leagues following a round-robin tournament format.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Entities](#entities)
- [Service Layer](#service-layer)
- [Repository Layer](#repository-layer)
- [License](#license)

## Features

- Manage Leagues, Teams, and Players
- Record match scores
- Generate and randomize match schedules for leagues
- Retrieve detailed information on leagues, teams, players, and matches

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- PostgreSQL 13 or higher
- Git

## Installation

1. **Clone the repository:**

   ```
   git clone https://github.com/your-username/football-management-application.git
   cd football-management-application
   ```
   
2. **Configure the database:**

  Update the application.properties file with your PostgreSQL database configuration.

  ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/football_db
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    spring.jpa.hibernate.ddl-auto=update
  ```

3. **Build the application:**
   
    ```
    mvn clean install
    ```

4. **Run the application:**

    ```
    mvn spring-boot:run
    ```

## Usage
Once the application is running, you can use tools like Postman or cURL to interact with the API endpoints.

## API Endpoints

Leagues
Get all leagues:

```
GET /leagues
```

Get a league by ID:

```
GET /leagues/{id}
```

Update a league:

```
PUT /leagues/{id}
```

Randomize and generate rounds and matches for a league:

```
POST /leagues/randomize/{id}
```

Teams
Get all teams:

```
GET /teams
```

Get a team by ID:

```
GET /teams/{id}
```

Create a new team:

```
POST /teams
```

Update a team:

```
PUT /teams/{id}
```

Players
Get all players:

```
GET /players
```

Get a player by ID:

```
GET /players/{id}
```

Create a new player:

```
POST /players
```

Update a player:

```
PUT /players/{id}
```

Matches
Get all matches:

```
GET /matches
```

Get a match by ID:

```
GET /matches/{id}
```

Update match score:

```
PUT /matches/{id}/score
```

## Entities

Player
+ id (Long)
+ name (String)
+ age (int)
+ position (String)
+ shirtNo (int)
+ currentTeam (Team)
+ currentSalary (double)

Team
+ id (Long)
+ name (String)
+ country (String)
+ league (League)
+ players (List<Player>)

League
+ id (Long)
+ name (String)
+ country (String)
+ teams (List<Team>)

Match
+ id (Long)
+ homeTeam (Team)
+ awayTeam (Team)
+ homeTeamGoals (int)
+ awayTeamGoals (int)
+ round (Round)

Round
+ roundNumber (int)
+ league (League)
+ matches (List<Match>)

## Service Layer
The service layer is divided into interfaces and their implementations:

+ LeagueService: Manages leagues and generate rounds and matches
+ TeamService: Manages teams.
+ PlayerService: Manages players.
+ MatchService: Manages matches and updates scores.
+ RoundService: Manage rounds

## Repository Layer
The repository layer includes interfaces that extend JpaRepository for CRUD operations:

+ LeagueRepository
+ TeamRepository
+ PlayerRepository
+ MatchRepository
+ RoundRepository

## License
This project is licensed under the MIT License - see the LICENSE file for details.

```
This README file provides an overview of your project, details on installation and usage, and documentation for your API endpoints. Adjust the GitHub repository URL and any other specific details as necessary.
```
