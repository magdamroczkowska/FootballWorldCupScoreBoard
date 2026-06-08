# Football World Cup ScoreBoard

A simple, clean, and fully tested Java implementation of a Football World Cup ScoreBoard.  
The project was developed using **Test‑Driven Development (TDD)** and focuses on clarity, correctness, and domain‑driven design principles.

---

## Features

- Start a new game between two teams
- Update the score of an ongoing game
- Finish a game and remove it from the scoreboard
- Display all ongoing games sorted by:
    1. Total score (descending)
    2. Creation time (most recent first)

---

## Domain Rules

- A team name cannot be blank or null
- A team cannot play against itself
- A team cannot participate in more than one game at the same time
- Scores cannot be negative
- Updating or finishing a non‑existing match results in an exception
- Team names are normalized (trimmed, capitalized, multi‑word support)

---

## Design Decisions

### **Team as a Value Object**
`Team` is implemented as a Java `record`:
- Always normalized
- Immutable
- Represents a domain value, not an entity
- Validation of blank and null names happens inside the record

### **Match**
Represents a single game:
- Holds teams, scores, and creation timestamp
- `createdAt` uses `System.nanoTime()` to guarantee deterministic sorting
- Contains score validation logic

### **ScoreBoard**
Responsible for:
- Managing matches
- Enforcing domain rules
- Sorting matches
- Validating API inputs (null checks)

### **Sorting Logic**
Matches are sorted by:
1. **Total score** (home + away), descending
2. **Creation time**, descending

This ensures:
- High‑scoring games appear first
- Newer games appear before older ones when scores are equal

---

## How to Run

This project uses **Java 21+** and **Maven**.