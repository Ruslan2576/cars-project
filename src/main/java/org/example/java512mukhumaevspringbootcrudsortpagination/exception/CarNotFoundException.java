package org.example.java512mukhumaevspringbootcrudsortpagination.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException() {
        super("Car with this id not found");
    }
}
