package agh.ics.oop.model.movement;

import agh.ics.oop.model.utilities.Vector2d;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString() {
        switch(this) {
            case NORTH: {
                return("N");
            }
            case NORTHEAST: {
                return("NE");
            }
            case EAST: {
                return("E");
            }
            case SOUTHEAST: {
                return("SE");
            }
            case SOUTH: {
                return("S");
            }
            case SOUTHWEST: {
                return("SW");
            }
            case WEST: {
                return("W");
            }
            case NORTHWEST: {
                return("NW");
            }
        }
        return null;
    }
    public Vector2d toUnitVector() {
        switch(this) {
            case NORTH: {
                return(new Vector2d(0,1));
            }
            case NORTHEAST: {
                return(new Vector2d(1,1));
            }
            case EAST: {
                return(new Vector2d(1,0));
            }
            case SOUTHEAST: {
                return(new Vector2d(1,-1));
            }
            case SOUTH: {
                return(new Vector2d(0,-1));
            }
            case SOUTHWEST: {
                return(new Vector2d(-1,-1));
            }
            case WEST: {
                return(new Vector2d(-1,0));
            }
            case NORTHWEST: {
                return(new Vector2d(-1,1));
            }
        }
        return null;
    }
    public MapDirection next() {
        switch(this) {
            case NORTH:
                return NORTHWEST;
            case NORTHWEST:
                return WEST;
            case WEST:
                return SOUTHWEST;
            case SOUTHWEST:
                return SOUTH;
            case SOUTH:
                return SOUTHEAST;
            case SOUTHEAST:
                return EAST;
            case EAST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
        }
        return null;
    }
    public MapDirection previous() {
        switch(this) {
            case NORTH:
                return NORTHEAST;
            case NORTHWEST:
                return NORTH;
            case WEST:
                return NORTHWEST;
            case SOUTHWEST:
                return WEST;
            case SOUTH:
                return SOUTHWEST;
            case SOUTHEAST:
                return SOUTH;
            case EAST:
                return SOUTHEAST;
            case NORTHEAST:
                return EAST;
        }
        return null;
    }
}
