# Akari

The classic Japanese puzzle game Akari (あかり) was second project I built when learning Java and the basics of object oriented programming. At the time of the project, I had just over 6 weeks of programming experience from writing my first `hello world!`.

Akari is ultimately derived from the continuative form of the verb 明る (akaru) meaning “to become bright/clear,” in turn giving way to the word generally meaning “light” or “lamp” (written as 明かり, 灯 or 燈)

The objective of the game is to place light bulbs on the grid in such a way that all the black cells are illuminated, and the following rules are satisfied:

1. A light bulb illuminates its own cell as well as any white cells in the same row or column until reaching a black cell or the edge of the grid.
1. Black cells cannot contain light bulbs.
1. Each white cell must be illuminated by exactly one light bulb.
1. Light bulbs cannot be adjacent to each other, neither vertically nor horizontally.

The repository also contains automated marking code and Test files written by Professor Lyndon While, the `CITS1001`, Introduction to Object Oriented Programming, unit coordinator

# Play Akari

Ensure that you have Java installed.

Compile game with `javac Akari.java`

After game has been successfully compiled, play game by running the command `java Akari` and following the prompt on the screen. After level has been selected, a window should pop up and allow you to play the game
