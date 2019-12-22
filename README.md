# Winter-Project-2019

## December 22, 2019, 14:00

This is a small project where I will make a checkers AI in java without any major tutorials or guides. This is meant to be used in a resume, to show how I am capable of self-motivation, problem solving, and programming on an intermediate level. I will be documenting my progress on this project as I proceed, including dates, my reasoning for specific ways of coding, and future plans until it is finished in a couple days.

Now for the start of this project, first thing that has to be done is initializing the repository, which includes setting up a java program that can be run and basic file management. I will be using the netbeans IDE, as I am familiar with it and is easy to use. I also chose to use java, even though I am also capable of programming in python and C++, since java is very easy to use when it comes to gui, while it is a nightmare in C++ and I don’t want to learn it in python right now, as the gui is not the main focus of this project.

The next thing, which I hope to finish today, is to initialize the starting checkers board, which I will get to as I finish setting up the repository.

## December 22, 2019, 14:30

Now that that part is finished, the next part is to initialize the board. I will be holding all the data for the game during runtime in a 8x8 array of integers, and then another 8x8 array of buttons that changes depending on what the corresponding integer in the other array is. For example, half of the button array will be white square buttons, which will have a corresponding 0 to identify it in the integer array. I will now program in the parts that make the initial database for the board, and then display it on the screen.

With all the data being stored in an array of integers, I made a key for what number is what data: 0 = white square, 1 = black square, 2 = white piece, 3 = black piece, 4 = white king, and 5 = black king. This means the starting array looks like this:

0 2 0 2 0 2 0 2

2 0 2 0 2 0 2 0

0 2 0 2 0 2 0 2

1 0 1 0 1 0 1 0

0 1 0 1 0 1 0 1

3 0 3 0 3 0 3 0

0 3 0 3 0 3 0 3

3 0 3 0 3 0 3 0

I made an algorithm that fills this in, which it does by looping through every element. It can tell when a spot is a white square when the x + y is an even number (for example, square [3][7] is white because 3 + 7 = 10 which is even). Also, the way I organized the array is such that the first number goes across, and the second goes down, so [0][7] would be the bottom left corner.


