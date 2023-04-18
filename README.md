# AstarEightPuzzle


goal_State = {1,2,3,8,0,4,7,6,5};
easy_State = {1,3,4,8,6,2,7,0,5};
medium_State = {2,8,1,0,4,3,7,6,5};
hard_State = {5,6,7,4,0,8,3,2,1};
worst_State = {5,6,7,4,0,8,3,2,1};
  
The following is a java implemenation of two A* Search glgorithms (heuristic functions) and tests them on the start states and goal
state shown above.
Only A* needs to detect duplicate states and eliminate them from the
remainder of the search.

  Function 1. A* search using the heuristic function h*(n), where h*(n) is thenumber of
  tiles out of place (not counting the blank).

  Function 2. A* search using the Manhattan heuristic function.

This implementation allows user to choose which state and method(function) of solving.
Prints the current state of the board as well as the length, cost, time, space, stats, iterations, and the time taken in miliseconds. 


# To run:
You will need to be able to run a java program.

Download all the files: Puzzle, Strategies, Heuriatics.

When running you can simply follow the promp from there!
Go through the different states and methods of solving. After each run you will be prompted to enter 1 or 0 depending on if you want to continue.



