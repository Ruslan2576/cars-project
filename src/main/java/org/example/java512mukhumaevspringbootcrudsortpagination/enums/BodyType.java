package org.example.java512mukhumaevspringbootcrudsortpagination.enums;

public enum BodyType {
    SEDAN("sedan"),
    HATCHBACK("hatchback"),
    COUPE("coupe"),
    CABRIOLET("cabriolet"),
    CROSSOVER("crossover"),
    MINIVAN("minivan"),
    PICKUP("pickup");

    private final String type;

    BodyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}