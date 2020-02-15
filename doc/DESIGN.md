# Simulation Design Final
### Names 
Michael Dodd (mmd61), Amjad Syedibrahim (as878), and Frank Tang (ft39)

## Team Roles and Responsibilities

Team Member #1 - Michael Dodd
* In charge of the simulation component of the project. This created outlining a master Cell class that would be extended for each of the different simulation types and writing a Grid class to store the cells.

Team Member #2 - Amjad Syedibrahim
* In charge of the configuration component of the project. This included creating the framework of the XML files and loading in the information from the files.

Team Member #3 - Frank Tang
* In charge of the visualization components of the project. This includes all things JavaFX related like the GUI and the making of the visualizations of the grids. Also worked on the intermediary class (Simulator.java) that took data from model to properly run the simulation.

## Design goals
#### What Features are Easy to Add
* The goal of this project was to create a program that can execute different simulations, such as a Game of Life simulation, Spreading of Fire simulation, Wa-tor World, etc. Since the program is mainly to act as an interface to simulate many different types of simulations, we planned on making several features to be easy to add to the program.
1) New Simulations - One of the primary goals of this project was to be able to easily add new types of simulations into the program. This is because this project is not specific to any single one type of simulation, as can be seen in how we are able to test out 6 different simulations (Game of Life, Spreading of Fire, Wa-Tor World, Segregation, Percolation, and Rock, Paper, Scissors. The newest type of simulation that we added was Rock, Paper, Scissors, and we did so by just making a new subclass of Cell in order to implement it. Thus, we were able to make the addition of new simulations to the program easily.
2) New Starting Configuration - Another of the primary goals of this project was to start each simulation based on an XML configuration file. This meant that we could not hard code any of the initial states of a simulation and would need to code a parser to read through the information given by the XML file. In the end, we were able to accomplish this with our Configuration class and can easily make new initial starting configuration for any type of simulation.
3) New Grid Shapes - As one of the features that were to be implemented in the second sprint of the project, we also wanted to make sure that the project could easily implement new shapes that would represent the cells of the simulation. To do this, we decided to use an inheritance structure with the ShapeGrid class, where the extended subclasses are RectangleGrid and HexagonGrid. Depending on what type of shape is called for in a simulation’s XML File, the appropriate shapes will be drawn in the grid. By using extended subclasses for drawing the shapes, we can thus easily create a new class that would represent another shape, such as a triangle or a trapezoid for example.

## High-level Design
#### Core Classes
Simulation:
* Grid class -  this is responsible for storing all the cells in the simulation. The grid fills itself in by initiating all of the new cells within it using the Cell constructor. The grid is updated from Visualization using the .updateGrid() method. This then updates each of the cells individually based on the grid currently being shown.
* Cell Masterclass: The cell masterclass is responsible for being the highest level for each of our simulations. All of the methods are the same except the update() method that is responsible for implementing the update method for each different type of simulation. The cells are each updated using their update() method that is written for each of the individual classes. The cell master class stores information such as its state, location in the grid, neighborhood information, and whether the simulation is hexagonal or toroidal. The cell is also responsible for determining the states of its neighbors.

Configuration:
* Configuration class: This class is responsible for loading in information from the XML files and is able to detect which type of simulation it is loading in. Based on the simulation type, it will tell the rest of the program what types of cells to create. It will also incorporate other features such as checking for errors in the xml file that is read in, randomizing initial cell states, and generating new xml files of the current state of a simulation. 

Visualization:
* SimulationWindow.java - This class is the instantiation of the simulation window. This object itself calls for the instantiation of the scene of the simulation program. The main purpose of this class is to allow us to make multiple simulation windows so that we can run more than one simulation side-by-side.
* UserInterface.java - This class is the heart of the visualization as it represents the graphical user interface with all the buttons and interactable pieces of the program. This part of the program also shows the user any errors that pop up, using the DisplayError object. This class is instantiated by the SimulationWindow class, and from this class, it creates and displays the Simulator.java class, which represents the controller to the simulation. 
* Simulator.java - This class acts as the intermediary between the UserInterface class and the Grid class. This is because it is instantiated by the UserInterface class when the user selects and loads a valid XML file. When this class is instantiated, it initializes the Configuration object, sends the initial data to the Grid class, displays the appropriate ShapeGrid (hexagon or rectangle) that represents the Grid class, and runs a timeline for the simulation to run on. This class also starts the SimulationGraph class to show the amount of cells in the simulation as time goes on. This is the last class of the view, as it is given data from the model in order to properly make a visual representation of the grid and simulation graph.

## Assumptions that Affect the Design
* One assumption that we made was that the bullet point for changing global simulation parameters was describing items like the speed of simulation/color. As a result, we made a slider that slowed down/sped up the simulation to fulfill this requirement, however, we learned after the deadline that these global simulation parameters were things like the probability in Fire or the energy in Wa-Tor World. As a result, we did not complete this portion of the design, but we know how to implement this (described later on in this DESIGN.md file).

#### Features Affected by Assumptions
* One feature that was affected by this assumption was that we did not give the user the ability to change global configuration parameters directly from the visualization panel.


## New Features HowTo

#### Easy to Add Features
* Add a new simulation: 
    * The most important step in creating a new simulation type is creating a new Cell type. To do this create a new class that extends Cell and implement the signature for the constructor and update method. Within the constructor call super and pass all parameters back into super. Within the update method, write whatever code is necessary to update the cells state. You will also need to add a new statement to the if-tree within the fillInitState() method in Grid. Within this if tree, set any global configuration parameters. If there are any global configuration parameters, create a new parse method to load in the parameters and implement new getter methods for the variables.
* Add a new starting simulation: 
    * In order to add a new starting simulation you will need to create a new XML file. You should follow the outline XML file for the type of simulation you are trying to create. Once you create a copy of the outline, you should change any of the tags you’d like to alter. The tag description should be self explanatory enough to know what their purpose is without individual descriptions.
* Add new grid shapes:
    * To add a new grid shape, a new class that extends the ShapeGrid superclass needs to be made. Since the superclass instantiates the grid as Polygon objects, any type of shape can be made using the JavaFX Polygon object. All that needs to be made in the extended class is the makeInitialShape() method, in which the programmer just needs to make an array of Points to create a new Polygon shape in the correct location in the grid (i.e. making a Polygon shape in the correct place so that it doesn’t overlap other shapes). Thus, it is relatively easy to make a new grid shape because we just need to make a new class, make a new Polygon shape, and then fine tune the shape’s starting positions so that they don’t overlap each other.

#### Other Features not yet Done
* One feature we were not able to add was the ability to add sliders on the visualization panel to affect the global configuration parameters. This feature was not completed because we did not know how to implement it, but rather it was because of a misinterpretation of the instructions (we thought that this feature was describing the slider that changed the speed at which the simulation ran at). However, we do know how to implement this if we were given the chance: to implement this, we would first remove specific global parameter values from simulations like Spreading of Fire or Wa-Tor World. Instead, the XML file would simply have a statement of a 1 or 0 to describe whether or not a specific slider needs to be created for that parameter. When the Configuration object is made, a new window with sliders for the specific global parameters would pop up. By maneuvering the slider, we would directly send a value to the Cell object to dynamically change the value of the global parameter. This would have been a very simple implementation, but we did not complete this feature because we thought that we had already completed this feature.
* Another feature that we did not get around to was the extensive error checking and handling. In the case of the xml file being corrupted, we were able to identify the error but did not give a default solution. A default solution would involve assigning default parameters to the values so that any type of simulation would run instead of the program telling the user to choose another option.

