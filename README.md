# Alto Intelligence Technical assignment

## Overview

This application simulates a restaurant seating management system. It manages tables of various sizes and clients (or
groups)
arriving at the restaurant. The system attempts to seat each group at the appropriate table based on their size and
availability. Groups that cannot be seated immediately are placed in a waiting queue and seated as tables become
available.

## Classes

### `Application`

The entry point of the application. It contains the `main` method, which demonstrates the functionality of
the `RestManager` class with a series of test cases.

### `RestManager`

Manages the seating of client groups and the waiting queue.

- **Fields:**
    - `tableMap`: Maps table sizes to lists of tables.
    - `waitingQueue`: Queue for groups waiting to be seated, sorted by group size.
    - `seatingMap`: Maps groups to their respective tables.

- **Methods:**
    - `onArrive(ClientsGroup group)`: Attempts to seat a group or adds them to the waiting queue.
    - `onLeave(ClientsGroup group)`: Handles a group leaving, either from a table or the waiting queue.
    - `lookup(ClientsGroup group)`: Looks up which table (if any) a group is currently seated at.

### `Table`

Represents a table in the restaurant.

- **Fields:**
    - `size`: Number of seats available at the table.
    - `availableSeats`: Number of available seats.

- **Methods:**
    - `canSeat(ClientsGroup group)`: Checks if the table can accommodate a given group.
    - `seatGroup(ClientsGroup group)`: Seats a group at the table, reducing the number of available seats.
    - `removeGroup(ClientsGroup group)`: Removes a group from the table, increasing the number of available seats.
    - `isFullyAvailable()`: Checks if the table is fully available (no seats taken).

### `ClientsGroup`

Represents a group of clients.

- **Field:**
    - `size`: Number of clients in the group.

## Test Cases

The `Application` class contains several test cases to demonstrate the functionality of the `RestManager`:

1. **Group of 1 arrives and should sit at the smallest table (2 seats).**
2. **Group of 2 arrives and should sit at the next available table (3-seat table).**
3. **Group of 3 arrives and should be seated at the 4-seat table.**
4. **Group of 4 arrives and should be seated at the 5-seat table.**
5. **Group of 5 arrives and should be seated at the 6-seat table.**
6. **Group of 6 arrives, but no table is available, should wait.**
7. **Check that the queue handles group 6 when group 1 leaves.**
8. **Group of 1 arrives again, should be seated at the 2-seat table.**
9. **Group 6 gets seated when a larger table is freed.**

## Algorithm and Complexity

### Algorithm

1. **Seating Groups:**
    - The algorithm first tries to seat the group at a table of the exact size.
    - If no exact match is available, it then tries to seat the group at larger tables.
    - If no fully available tables are found, it checks if any table can accommodate the group based on available seats.

2. **Processing the Waiting Queue:**
    - When a group leaves, the waiting queue is processed to see if any of the waiting groups can now be seated.

### Complexity

- **Constructor (`RestManager(List<Table> tables)`):** `O(T)`, where `T` is the number of tables.
- **`onArrive(ClientsGroup group)`:** `O(log W + L)`, where `W` is the number of waiting groups and `L` is the number of
  tables.
- **`onLeave(ClientsGroup group)`:** `O(log W + W * L)`, where `W` is the number of waiting groups and `L` is the number
  of tables.
- **`lookup(ClientsGroup group)`:** `O(1)`.
- **`seatGroup(ClientsGroup group)`:** `O(L)`, where `L` is the number of tables.
- **`processQueue()`:** `O(W * L)`, where `W` is the number of waiting groups and `L` is the number of tables.o