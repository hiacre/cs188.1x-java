Your interface to the pacman world
==================================
The following classes contain the parts of the code that you will need to
understand in order to complete the project.

   AgentAbstract, Direction, ConfigurationStandard, AgentStateSimple,
   GameState1, Grid, GridStandard, GridCapsules


The hidden secrets of pacman
============================
All of the logic code that the pacman environment uses to decide who can move
where, who dies when things collide, etc.  is contained in the following
classes.  You shouldn't need to read this code, but you can if you want.

   ClassicGameRules, PacmanRules, GhostRules


Framework to start a game
=========================
The class Pacman contains the code for reading the command you use to set up
the game, then starting up a new game, along with linking in all the external
parts (agent functions, graphics).  Read this class to see all the options
available to you.


To play your first game, type 'python pacman.py' from the command line.
The keys are 'a', 's', 'd', and 'w' to move (or arrow keys).  Have fun!

