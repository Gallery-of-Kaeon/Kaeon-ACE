[Home](https://github.com/Gallery-of-Kaeon/Kaeon-ACE/blob/master/README.md)

# The ACE Game Engine Model

The ACE game engine model specifies a certain type of Philosopher's Atlas upon which a modular and robust game engine can be implemented.

The contents of this document require the reader to be familiar with the Philosopher's Stone and the Philosopher's Atlas.
If you are unfamiliar with either of these, please review the contents of this repository before proceding:

[Philosopher's Stone](https://github.com/Gallery-of-Kaeon/Philosophers-Stone/blob/master/README.md)

## Philosophy

The ACE game engine model is built on the concept of component based entities.

Everything in the game is an entity.
An entity is nothing more than a conatiner for a series of components.
The components stored within entities store data, but do not perform operations.
Entities may also be connected in parent child relationships.
Of course, no entity may have more than one parent.

The functionality of the engine come from processes.
Each process operates independently of one another,
reading and modifying the content of the entity's components.

## Structure

The ACE game engine model is a Philosopher's Atlas made up of four different types of Philosopher's Stones.
The stone types can be easily distinguished by their connections to each other.

### The ACE Stone

The ACE stone is the core of the model,
providing an interface between the processes and the entities.
There is only one ACE stone,
and it does not store any data nor does it perform any operations.

All processes are connected to it through public mutual connections,
and the ACE Stone is publicly connected to any entity that does not have a parent entity.

### Entity Stones

An Entity stone represents a component based entity.
It does not store any data nor does it perform any operations.
It can be connected to various component stones with public mutual connections.
It can also be connected to various other entity stones which act as its children with one way public connections.
No entity stone may have more than one parent.

### Component Stones

A component stone represents a component of a component based entity.
It may store data but may not perform operations.
It will be connected to one and only one Entity stone with a public mutual connection.

### Process Stones

A Process stone perfoms operation on the entities represented by the entity stones.
A Process stone may store data or perform operations, and will be connected to the ACE Stone with a public mutual connection.

## Extraneous Connections

Any connection to or from an ACE,
Entity,
Component,
or Process stone not described in the description above must be private.