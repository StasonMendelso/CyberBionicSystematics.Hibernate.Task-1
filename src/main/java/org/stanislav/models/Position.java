package org.stanislav.models;

/**
 * @author Stanislav Hlova
 */
public enum Position {
    DIRECTOR("Director"), MANAGER("Manager"), WORKER("Worker");
    private final String name;

    Position(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Position getInstance(String positionName) {
        for (Position position : Position.values()) {
            if (position.name.equals(positionName)) {
                return position;
            }
        }
        throw new RuntimeException(String.format("No Position was found for position with name \"%s\"", positionName));
    }
}
