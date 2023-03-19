package org.stanislav.models;

/**
 * @author Stanislav Hlova
 */
public enum MaritalStatus {
    SINGLE("Single"), MARRIED("Married");
    private final String name;

    MaritalStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MaritalStatus getInstance(String maritalStatusName) {
        for (MaritalStatus maritalStatus : MaritalStatus.values()) {
            if (maritalStatusName.equals(maritalStatus.name)) {
                return maritalStatus;
            }
        }
        throw new RuntimeException(String.format("No MaritalStatus was found for marital status with name \"%s\"", maritalStatusName));
    }
}
