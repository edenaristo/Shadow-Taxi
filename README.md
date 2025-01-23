# ShadowTaxi

ShadowTaxi is a Java-based taxi simulation game developed as part of the **SWEN20003 Object-Oriented Software Development** course at The University of Melbourne. This project expands upon the functionality of Project 1 to introduce new game elements, mechanics, and challenges.

---

## Overview

In **ShadowTaxi**, players take on the role of a taxi driver navigating an endless road. The goal is to survive economic hardships by:
- Picking up passengers
- Dropping them off at their destinations
- Avoiding collisions with enemy and other cars

The game tests players' strategy and reflexes as they aim to beat a target score of 500 before time runs out.

---

## Game Features

- **Dynamic Gameplay**: Navigate through lanes, pick up passengers, and avoid hazards.
- **Score System**: Earn points based on passenger priority, distance traveled, and collected coins.
- **Health Management**: Monitor the health of the driver, taxi, and passengers to avoid game-over scenarios.
- **Weather Effects**: Experience changing weather conditions that influence gameplay.
- **Invincibility Power-Ups**: Gain temporary immunity to collision damage.
- **Game Screens**:
  - Home Screen
  - Player Information Screen
  - Game Play Screen
  - End Screen (Top 5 Scores Display)

---

## Usage

To play the game:
- Clone the repository
- Use the provided pom.xml for Maven dependencies
- Run the game using the ShadowTaxi main class

## Gameplay

### Objective
Achieve a score of **500 or higher** before the game ends.

### Key Mechanics
- Pick up passengers and drop them off at their **trip-end flags**.
- Collect **coins** to boost passenger priority.
- Avoid collisions with **enemy cars**, **fireballs**, and other obstacles.
- Manage **health** for all entities to avoid losing the game.

### Loss Conditions
- **Taxi health** drops to zero.
- **Passenger or driver health** drops to zero.
- Time exceeds **15,000 frames** without reaching the target score.

---

## Scoring System

- **Distance Fee**: Earn points based on the distance traveled during a trip.
- **Priority Fee**: Higher-priority passengers yield more points.
- **Penalties**:
  - Applied for **overshooting trip-end flags**.
  - Applied for **damaging the taxi**.


## Acknowledgments

This project was developed as part of the SWEN20003 Object-Oriented Software Development course at The University of Melbourne. Special thanks to the course coordinators and teaching staff.
