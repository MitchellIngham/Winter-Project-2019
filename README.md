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

## December 22, 2019, 16:00

The next part is to initialize the gui, which will be made of a similar two dimensional array of buttons, and then another function which changes the button icons to their corresponding value in the data array. 

## December 22, 2019, 20:30

The gui is almost done, there is now an 8x8 grid of buttons that are displayed on the screen. For now, when they are pressed, they print the location of the button in array terms, which it gets by taking the location of the button and dividing by the width of the button. This way the program will be able to tell where the player is clicking, which will be mandatory later. But now, the last thing I want to do today is make a function that updates the button icons so that it looks like a checker board and not 64 buttons. This function will be run every time a move is made, so that the board changes as the game progresses.

## December 22, 2019, 21:00

The gui is now completed, and there is a visible board on screen. This is all I wanted to accomplish today, and tomorrow I will start on something closer to the main focus of this project. Tomorrow’s goal is to make it possible for you, the player, to legally move. For now, here is what the starting board looks like.

![Starting Board](https://imgur.com/0bmVXfS.png)

## December 23, 2019, 13:00

To start working towards my goal for today, the first thing I have to do is recognize when the player clicks on one of their own pieces. My plan is the selected piece has a green background, and after that is done any legal moves will be green squares. Then the player can click on the green square, and it will move the piece.

## December 23, 2019, 13:30

Now the game lets you select a piece, with that piece changing if you click a different one. Now it is time to make the algorithm to find all legal moves.

![Selected Piece](https://imgur.com/sbG8oLJ.png)

This algorithm will take a while longer than the other parts, because of how many things it has to find. For now I will start with it only finding the legal moves for black, so that we can get to some moving pieces faster. The first moveset I’ll program is the regular black piece, which includes passive moves, moves where it takes pieces, and moves where it takes multiple pieces.

## December 23, 2019, 16:30

It took a while and is very math heavy, but now the black player is able to move their pieces in every legal way, except for taking multiple pieces, which is a bit complicated. This is the last thing I want to code today, and then tomorrow I can start on the AI for the white player. Here is what it looks like for now.

![Able Moves](https://imgur.com/U5zhGtb.png)
