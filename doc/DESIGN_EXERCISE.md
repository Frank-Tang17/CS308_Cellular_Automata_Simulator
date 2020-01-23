Names: Michael Dodd, Frank Tang
NetID: mmd61, ft39

# DESIGN_EXERCISE

### Inheritance Review
For inheritance in the Breakout game, both of us discussed that we should have used inheritance on the power ups and the brick classes, instead of having them be in their specific classes. 
This is because one of us, for example, had power ups being generated with a random number generator whenever a brick was broken. However, the implementation of this power up system had it so that the power up being generated had its ID type being generated at the same time as well; this ID type needed to be passed to other classes in order to determine the effect that occurs. By having  a super class that is called powerUp.java with abstract methods for the effect and the image, each separate power up can have its own extended subclass that would hold its own image and its effect in the game. 
In the other project where power ups are predetermined, inheritance can be used with the bricks so that when the brick configuration file is read, the superclass Bricks will be able to have subclasses for regular bricks and for bricks that have the predetermined power ups. This would decrease the amount of clutter that occurs in the program and would allow for better implementation of new power ups and brick types.

### Simulation High Level Design

We both agreed that a 2D array seemed like the simplest method to be able to find the neighbors of the current cell. Since we can just add and subtract one from the indexes, checking the surroundings is easy.

1.  How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?
	* There would need to be two versions of the grid. One of them is the old state and one of them is the new state based on the old state. Then the old will become the new and the relationships with continue
2.  What relationship exists between a Cell and a simulation's rules?
	* There would be one update method for all the cells no matter what their position is since the rules are only dependent on the surroundings. The cell needs to follow the rules.
3.  What is the grid? Does it have any behaviors? Who needs to know about it?
	* The grid is a structure holds a all the cells. We are thinking of using a 2D array.
	* The grid will update itself based on the old state of the grid
	* Configuration will need to know about it
4.  What information about a simulation needs to be in the configuration file?
	* The initial state of each cell in the simulation and the general characteristics of the cells such as height, width, and shape 
5.  How is the graphical view of the simulation updated after all the cells have been updated?
	* Each cell should update itself stead of the passing the whole grid to the simulation.

### Class-Responsibility-Collaborator Cards

### Use Cases


> Written with [StackEdit](https://stackedit.io/).