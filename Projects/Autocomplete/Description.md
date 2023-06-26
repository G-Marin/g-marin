# Autocomplete

## Description
The Autocomplete project is a Java program that brings the power of autocomplete to your fingertips. It predicts and suggests completions for a given set of queries, arranged in descending order of relevance.

Autocomplete is a widely-used feature in modern applications, making user experiences smoother and faster. Imagine typing a query and having the program intelligently predict and display the most likely completions as you go. Autocomplete is commonly found in applications like the Internet Movie Database (IMDb), search engines, and even on cell phones to speed up text input.

## Features
- Swift and efficient predictions of completions based on a given prefix.
- Relevant suggestions displayed first, thanks to intelligent ranking based on query weight.

## Technologies Used
- Java

## File Descriptions
- `Autocomplete.java`: The heart of the project, this file contains the implementation of the autocomplete algorithm. It includes methods for querying completions based on a given prefix and sorting the results by weight.
- `Term.java`: This file defines a term consisting of a query string and an associated weight. It provides convenient methods to access and modify the term's properties.
- `BinarySearchDeluxe.java`: A utility class that offers binary search operations on arrays of `Term` objects.
