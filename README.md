# Zork 2

## Summary and Goal
The player is a Thief who breaks into a house to steal from a safe. \
The house has different rooms, each will be randomly generated with different items, notes, and people. \
The player can pick up notes without limit, but can only carry a limited number of items. \
The number of items the player can carry depends on the weight of the item, as well as if the player has a bag or not. \
The player can hold either 2 items with a total weight of up to 4 in their hands. (One item per hand) \
The backpack allows the player to carry an additional 3 items, regardless of their weight. \
Certain actions can only be done if the player has at least one hand free. \
The player can also find a bag in one of the rooms, which will increase their carrying capacity by 3. \
Storing and retrieving items from the backpack takes time. \
Items that are stashed in the backpack cannot be used until they are retrieved. \
The player can find notes in some rooms, which may contain clues about the location of the safe, or how to open it. \
Each action takes a certain amount of time.

**Win condition** \
The goal is to find and open the safe and leave the house with its contents within a given time-limit. 

**Fail Condition** \
If the time limit runs out before the player can retrieve the contents of the safe and leave the house, the game is over.

## Rooms

Cellar \
    Has only 1 door. \
    Can contain between 1 and 2 items. \
    Contains no notes.

Kitchen \
    Has 3 doors. \
    Can contain between 0 and 1 items. \
    Can contain between 0 and 1 notes.

Attic \
    Has 1 door. \
    Can contain between 1 and 3 items. \
    Can contain between 0 and 1 notes.

Bathroom \
    Has 1 door. \
    Can contain between 0 and 1 items. \
    Can contain between 0 and 1 notes.

Bedroom \
    Has 1 door. \
    Can contain between 1 and 3 items. \
    Always contains 1 note.

Office \
    Has 2 doors. \
    Has no Items. \
    Always contains 1 note.

Front Yard (Starting Room) \
    Has 1 door. \
    Has no items. \
    Has no notes.

Dining Room \
    Has 2 doors. \
    Contains no items. \
    Can contain between 1 and 2 notes.

Basement \
    Has 1 door. \
    Can contain between 1 and 3 items. \
    Contains no notes.

Living Room \
    Has 3 doors. \
    Can contain between 2 and 3 items. \
    Can contain between 0 and 1 notes.

## Items

Key \
    Weight: 1 \
    Description: A small metal key that looks like it could open a lock.

Backpack \
    Weight: 0 \
    Description: A sturdy backpack that can hold several items.

Flashlight \
    Weight: 1 \
    Description: A small flashlight that can illuminate dark areas.

Crowbar \
    Weight: 3 \
    Description: A sturdy crowbar that can be used to pry open doors or windows.

Lock pick Set \
    Weight: 1 \
    Description: A set of lock picks that can be used to unlock doors or safes. Has a chance of breaking when used.

Knife \
    Weight: 2 \
    Description: A sharp knife that can be used for cutting through objects.

## Commands

help \
    Description: Displays a list of available commands and their descriptions. \
    Time: 0 min

move <direction> \
    Description: Moves the player in the specified direction (north, south, east, west) \
    Time: 15 min \
    Example: move north

scan \
    Description: Scans the current room and provides a description of the room, including what furniture it contains \
    Time: 0 min \
    and if anyone occupies it.

check <furniture> \
    Description: Examines the specified furniture and reveals any hidden items if the right conditions are met. \
    Time: 5 min \
    Example: check table

take <item> \
    Description: Takes the specified item and adds it to the player's inventory if they have enough space. \
    Time: 5 min \
    Example: take key

drop <item> \
    Description: Drops the specified item from the player's hand and leaves it in the current room. \
    Time: 0 min \
    Example: drop key

stash <item> \
    Description: Stashes the specified item in the player's backpack if they have one and if there is enough space. \
    Time: 5 min \
    Example: stash key

fetch <item> \
    Description: Retrieves the specified item from the player's backpack if they have one and if the item is in the backpack. \
    Time: 5 min \
    Example: fetch key