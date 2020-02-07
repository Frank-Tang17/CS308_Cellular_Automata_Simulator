# Refactoring Discussion 
### Frank Tang (ft39), Michael Dodd (mmd61), and Amjad Syedibrahim (as878)
### 2/6/2020

##Discussion:
During this lab, we focused on discussing some design issues and refactoring our code for the future
of the project. The main focus of our design issues is further separating the model and view from each other.
For example, we were told that our cells should not have a Rectangle associated to them; the view should be the one to handle
drawing the Rectangles and their colors. The cell and Grid object should be returning just an array of data and then the
view should handle the drawing of the cells. In addition, we decided that we need to rearrange how we pass our information
from the array. Specifically, we should stop passing around the 2D array from a Grid object class, and get the specific points
necessary to run the different simulations.

In addition to these design issues, we refactored our code in accordance with the errors that were pointed out 
via the design.cs.duke.edu website. These are the following refactors for the following sections of this project:

1. View -
    * UserInterface.java - This class had the most errors in the view part of the project. The main issues found in this
    class was variables that were not private or they were not being used. As a result, I chose to refactor all of the issues
    because it was left over code borrowed from other sources given to us from class, such as the Lab Browser lab
    * Simulator.java - This class had minimal errors, so I chose to refactor all of these too. These issues were just
    making some variables final because they represent constants and the other issue was removing a piece of unused code.

2. Model - 
    * Had a lot of private final variables that needed to be made static, so this was an easy change.
    * Had a lot of public variables that needed to be made private. This change was easy as well because it needs
    to be private for the final submission.
    * Got rid of the Grid class's getGrid() method to make sure that we are not passing the grid around, and that
    we should be passing specific locations of the grid.
3. Configuration - 
    * Got rid of a lot of duplicated code by making a couple of new methods, so each separate simulation now has very little
    in their methods for parsing through specific simulations
    * Made the code easier to read overall