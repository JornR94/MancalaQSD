package com.jornrigter.moves;

public class InvalidMoveException extends Exception {

    public enum Type {

        CANT_PICK_COLLECTION_HOLE,
        CANT_PICK_HOLE_OTHER_PLAYER,
        INVALID_HOLE, // Hole is not within range that can be picked on the board (1-14)
        CANT_PICK_EMPTY_HOLE;

        public String getErrorMessage() {
            switch (this) {
                case CANT_PICK_COLLECTION_HOLE:
                    return "You cannot pick a collection hole";
                case CANT_PICK_HOLE_OTHER_PLAYER:
                    return "You cannot pick the hole of another player";
                case INVALID_HOLE:
                    return "Invalid hole given";
                case CANT_PICK_EMPTY_HOLE:
                    return "You cannot pick an empty hole";
                default:
                    throw new IllegalArgumentException("Unknown Type provided: " + this);
            }
        }
    }

    private Type type;

    public InvalidMoveException(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
