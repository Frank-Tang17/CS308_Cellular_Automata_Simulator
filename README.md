simulation
====

This project implements a cellular automata simulator.

Names: Michael Dodd (mmd61), Frank Tang (ft39), Amjad Syedibrahim (as878)

### Timeline

Start Date: 1/23/2020

Finish Date: 2/9/2020

Hours Spent: 80

### Primary Roles
* Simulation: Michael Dodd
* Visualization: Frank Tang
* Configuration: Amjad Syedibrahim

### Resources Used
* In-class readings
* Stack overflow
* Java Orcale 

### Running the Program

Main class: Simulation Application

Data files needed: You will need at least one properly formatted XML file in the resources directory. You will also need the style sheet and the english properties file. 

Features implemented:

Simulation:
* Added the ability to use toroidal edge conditions for all simulations and shapes. You can turn on toroidal edges by changing the appropriate tag in the XML file from 0 to 1.
* Implemented hexagonal shapes for cells and used 6 neighbors. You can turn on hexagonal cells by changing the appropriate tag in the XML file from 0 to 1.
* You can implement any type of neighborhood by changing the <neighborColIndex> and <neighborRowIndex> tags in the XML file.
* Created Rock Paper Scissor simulation in addition to the other required simulations

Configuration:
* We can check the configuration file for invalid simulation types or no simulation types given at all
* We can check the cell state values for invalid numbers that are not allowed
* The initial configuration states can be randomized for any of the simulations based on a toggle button ("Randomize Simulation" Button) followed by the reset button.
* At any point during the simulation, the current state as well as the other variables of the simulation can be exported to an XML file  
Color of the cells can be changed from the XML file

Visualization:
* We display a graph of stats about the populations of all of the "kinds" of cells over the time of the simulation
* We allow users to interact with the simulation dynamically to change the values of its parameters. This is done by using the sliders to speed up the simulation and slow down. You can also step forward.
* We allow users to interact with the simulation dynamically to change the values of its parameters. This is done by pressing the number key associated with the state you want to change cells to and then clicking on the cells you want to change.
    * Specifically, the following keys will act as the following:
        * "1" key will set the dynamic state changer value to state 0 so that a cell's state will change to 0 when the cell is clicked
        * "2" key for setting state 1 when clicked
        * "3" key for setting state 2 when clicked
* We implemented features to allow a user to run multiple simulations side by side. This can be done by clicking the “New Simulation Window”.




### Notes/Assumptions

Assumptions or Simplifications:
* Assumes the person knows how the XML files are formatted
* We simplified our hexagon so that it was not flexible with neighbors. The neighborhood is hard coded into the program.
* We export Segregation and Rock, Paper, Scissor in the same way which assumes that they are functionally the same simulation 

Interesting data files:
* We used XML for our cell configurations
* We used CSS styling sheets for our visualization
* We used an English resources file as our language 

Known Bugs:
* If the grid is smaller than 3x3 things will get weird
* The graph for each simulation do not close by themselves when a new simulation is loaded
* We can add a state 2 in a simulation that only has 2 states. For example, you can add a cell state that
does not exist in the GameofLife Simulation, which may break the code.

Extra credit:
* We implemented all of the above features from the Complete portion of the assignment. We addressed every major bullet point and implemented at least one sub bullet point.


### Impressions
We really enjoyed the project. It was interesting to see how it was fairly easy to add new features to our code in the Complete portion because we made our code flexible during the Basic Implementation.



