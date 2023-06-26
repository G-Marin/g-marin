# Decoment

## Description
Decoment is a C program that acts as a Linux filter, removing comments from C source code. It reads text from the standard input stream, removes comments, and writes the modified text to the standard output stream. This program handles the de-commenting task by replacing comments with spaces while preserving line numbering and string/character literal boundaries.

## Features
- Removes comments from C source code.
- Preserves line numbering in the output.
- Maintains string and character literal boundaries.
- Acts as a Linux filter, reading from the standard input and writing to the standard output.
- Provides error and warning messages to the standard error stream.

## Technologies Used
- C programming language
- Linux environment

## File Descriptions
- `decomment.c`: The main file of the project, containing the implementation of the de-commenting functionality. It reads text from the standard input stream, removes comments, and writes the modified text to the standard output stream.
- `dfa`: Textual representation of the DFA (Deterministic Finite Automaton) implemented for de-commenting.
- Test files: A collection of text files used to test the functionality of the program.
