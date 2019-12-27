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

## December 23, 2019, 20:00

The player is finally able to do multi jump moves. It was more complicated than all of the other moves combined, and I’ll explain why. First it has to display them when you click on a piece, which uses a recursive function that as long as it finds a place to jump to, it will search for other places to jump from that point. And then, when you click on a green square that uses a multi jump move, it uses a while loop that runs as long as the jump isn’t completed. This while loop uses another recursive function that looks at all possible moves from a move, and returns true if it is possible to end up at the destination to finish the move. The while loop checks this, and if it returns true, it moves there. After that, it checks again, so that it never goes into a dead end.

Also, I have realized today that there is a rule in checkers that says whenever a player can take a piece, they must take a piece. I did not know about this rule, and coded this program in such a way that it would be very difficult to code this rule into it. Therefore, I will ignore the rule for the sake of time. Also the suspension of this rule allows for interesting games.

My goal for tomorrow is to start on the white player AI, and at least have them making random moves by the end of the day. I have completed everything I wanted for today, and I will continue tomorrow.

## December 26, 2019, 12:30

I decided to take a two day break for the holidays, but I am back to work now. My goal for today is to make the white player make random legal moves. For this I will have to store all the legal moves, and choose one randomly. Because there is no way to know how many moves there are before calculating them, the best way to store them is in a two dimensional vector, where it is a vector of vectors representing all the legal moves for one piece each. I can mostly use the same algorithm as the black player, except changing which direction the pieces go in and what color the enemy is. This should be a shorter day, as I have had a while to think about how to implement this over the holiday.

## December 26, 2019, 18:00

I’ve finished making the white player move, although he isn’t very smart because he makes random moves right now. Tomorrow I’ll make a system for an algorithm to rate each move by how good it is, and then the AI picks the best one. Here is a picture of the white player doing a double capture and making a king.

![doublejump1](https://imgur.com/zGMKlab.png)

![doublejump2](https://imgur.com/7fL6r90.png)

## December 27, 2019, 11:30

Today I’ll be looking at making an algorithm that looks at every white move, and gives it a numerical rating on how good it is. After that, it picks the one with the highest score, instead of picking a random one. There are many different situations to look at to give a move a rating, but it is much simpler once you break it down into smaller parts.

The first, and simplest situation is looking at where the piece will end up. When two moves are equal, I want it to pick the one that is closer to the end of the board, prioritizing making more kings over pieces at the back of the board. So this will give a move a number from 1-7, depending on how far forward the piece is moving. This of course does not apply to kings, and they will instead prioritize moves that result in being next to an enemy, giving it a rating of 5, a rating of 4 when 2 away, and a rating of 3 when 3 away.

The second situation to consider is where the piece is leaving. Making a jump could let the black player take a piece that would otherwise be safe. So, if a black player can take a piece as a result of a move, it will subtract 10 from the rating. However, if the white player can take the black piece, it will add 10 to the rating, as it is a fair trade. This will work similarly for multi jump moves, as baiting the black player into taking a piece could allow for white to take multiple black pieces, or allowing black to take multiple pieces. Also, if moving the piece would save it from potentially being captured, then it will add 10 to the rating.

The third situation is for passive moves. This will make sure that white does not make passive moves that will let black take a free piece. It will use the same algorithm as the last step, where it looks at a tradeoff and tells how advantageous it is for the white player. If the passive move doesn’t move next to a black piece, it doesn’t change the rating.

The fourth is for taking single pieces. It again uses the same tradeoff algorithm and tells if it is a good move.

The fifth, and final situation for now, is for multi jump moves. You can probably guess what algorithm it uses to tell if it is a good move or not.

After all of these situations are covered, I will probably have an ok AI to play against.

## December 27, 2019, 17:00

It’s taken me a while, but I set up the system where it can rate each move, then pick the highest one. It took a lot longer than I expected, as there were a few vector bugs that somehow weren’t a problem when I was using them yesterday. I Also implemented the first of the situations that I listed earlier, and it’s made for an annoying AI so far. I will finish half of the second situation, where it stops pieces from moving if it will give the black player an opportunity to take a piece. All of the other situations depend on a complicated tradeoff algorithm that I plan to code tomorrow, finishing the project.

## December 27, 2019, 18:00

I finished half of the second situation, I’ll program the tradeoff algorithm tomorrow, which I can plug into pretty much every other situation, so as soon as it is finished, which could take a while, the whole project will come together very quickly.
