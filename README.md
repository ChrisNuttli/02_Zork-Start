# Zork 2

## Summary and Goal
The player is a Thief who breaks into a house to steal from a safe.
The house has different rooms, each will be randomly generated with different items, notes, and people.
Some rooms may be occupied by residents who will try to catch the player if they notice them.
The player can pick up notes without limit, but can only carry a limited number of items.
The number of items the player can carry depends on the weight of the item, as well as if the player has a bag or not.
The player can hold either 2 items with a total weight of up to 4 in their hands. (One item per hand)
The backpack allows the player to carry an additional 3 items, regardless of their weight.
Certain actions can only be done if the player has at least one hand free.
The player can also find a bag in one of the rooms, which will increase their carrying capacity by 3.
If a resident notices the player, they will chase the player and try to catch them.
The player can hide in certain rooms, that contain hiding spaces to avoid being caught.
Storing and retrieving items from the backpack takes time, and the player may be caught if they are not careful.
Items that are stashed in the backpack cannot be used until they are retrieved.
The player can find notes in some rooms, which may contain clues about the location of the safe, or how to open it.

The goal is to find and open the safe in the house and steal its contents without getting caught.
The game ends when you either get caught (game over) or manage to leave the house with the contents of the safe (you win).

## Rooms

Cellar
    Has a hiding space.
    Has only 1 door.
    Can contain between 1 and 2 items.
    Contains no notes.

Kitchen
    Has no hiding space.
    Has 3 doors.
    Can contain between 0 and 1 items.
    Can contain between 0 and 1 notes.
    Can be occupied by 1 person.

Attic
    Has no hiding space.
    Has 1 door.
    Can contain between 1 and 3 items.
    Can contain between 0 and 1 notes.

Bathroom
    Has a hiding space.
    Has 1 door.
    Can contain between 0 and 1 items.
    Can contain between 0 and 1 notes.

Bedroom 
    Has no hiding space.
    Has 1 door.
    Can contain between 1 and 3 items.
    Always contains 1 note.
    Always occupied by 1 or 2 people.

Office
    Has a hiding space.
    Has 2 doors.
    Has no Items.
    Always contains 1 note.
    Can be occupied by 1 person.

Front Yard (Starting Room)
    Has no hiding space.
    Has 1 door.
    Has no items.
    Has no notes.
    Is never occupied by any people.

Dining Room
    Has a hiding space.
    Has 2 doors.
    Contains no items.
    Can contain between 1 and 2 notes.
    Can be occupied by up to 2 people.

Basement
    Has a hiding space.
    Has 1 door.
    Can contain between 1 and 3 items.
    Contains no notes.
    Is never occupied by any people.

Living Room
    Has no hiding space.
    Has 3 doors.
    Can contain between 2 and 3 items.
    Can contain between 0 and 1 notes.
    Can be occupied by up to 2 people.

## Items

Key
    Weight: 1
    Description: A small metal key that looks like it could open a lock.

Backpack
    Weight: 0
    Description: A sturdy backpack that can hold several items.

Flashlight
    Weight: 1
    Description: A small flashlight that can illuminate dark areas.

Crowbar
    Weight: 3
    Description: A sturdy crowbar that can be used to pry open doors or windows. Will make noise when used.

Lock pick Set
    Weight: 1
    Description: A set of lock picks that can be used to unlock doors or safes. Has a change of breaking when used.

Hand Mirror
    Weight: 2
    Description: A small hand mirror that can be used to see around corners or behind objects.

Knife
    Weight: 2
    Description: A sharp knife that can be used for cutting through objects.

## Commands

help
    Description: Displays a list of available commands and their descriptions.

move <direction>
    Description: Moves the player in the specified direction (north, south, east, west)
    Example: move north

scan
    Description: Scans the current room and provides a description of the room, including what furniture it contains and if anyone occupies it.

examine <furniture>
    Description: Examines the specified furniture and reveals any hidden items if the right conditions are met.
    Example: examine table

take <item>
    Description: Takes the specified item and adds it to the player's inventory if they have enough space.
    Example: take key

drop <item>
    Description: Drops the specified item from the player's inventory and leaves it in the current room.
    Example: drop key

stash <item>
    Description: Stashes the specified item in the player's backpack if they have one and if there is enough space.
    Example: stash key

retrieve <item>
    Description: Retrieves the specified item from the player's backpack if they have one and if the item is in the backpack.
    Example: retrieve key