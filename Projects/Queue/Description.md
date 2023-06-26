# Queues

## Deque Description
The Deque project is a Java program that implements a double-ended queue (deque) data structure. It allows adding and removing items from either the front or the back of the collection. The program provides a generic data type called `Deque` that supports various operations such as adding and removing items, checking if the deque is empty, and iterating over the items.

## Randomized Queue Description
The Randomized Queue project is a Java program that implements a randomized queue data structure. It is similar to a stack or queue, but the item removed is chosen uniformly at random among the items in the collection. The program provides a generic data type called `RandomizedQueue` with operations for adding and removing items, sampling a random item without removal, and iterating over the items in a random order.

## Features
- **Deque**
  - Add and remove items from the front or back of the deque.
  - Check if the deque is empty and get the number of items.
  - Iterate over the items from front to back.

- **Randomized Queue**
  - Add and remove items in a randomized order.
  - Sample a random item without removing it.
  - Iterate over the items in a random order.

## Technologies Used
- Java

## File Descriptions
- `Deque.java`: Implements the `Deque` class, representing the double-ended queue data structure. It provides methods for adding and removing items from the front or back, checking the deque's empty status, and iterating over the items.
- `RandomizedQueue.java`: Implements the `RandomizedQueue` class, representing the randomized queue data structure. It provides methods for adding and removing items in a randomized order, sampling a random item, and iterating over the items in a random order.
- `Permutation.java`: A client program that takes an integer as a command-line argument, reads a sequence of strings from standard input, and prints exactly k of them uniformly at random. Each item from the sequence is printed at most once.
