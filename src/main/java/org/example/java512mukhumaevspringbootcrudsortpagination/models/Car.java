package org.example.java512mukhumaevspringbootcrudsortpagination.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.java512mukhumaevspringbootcrudsortpagination.enums.BodyType;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Это обязательное поле к заполнению")
    private String marka;

    @NotBlank(message = "Это обязательное поле к заполнению")
    private String model;

    @NotBlank(message = "Это обязательное поле к заполнению")
    private String color;

    @Min(value = 0, message = "Скорость не может быть меньше чем 0")
    @Max(value = 350, message = "Скорость не может быть больше чем 350")
    private int speed;

    @Min(value = 2, message = "Кол-во мест не может быть меньше чем 2")
    @Max(value = 10, message = "Кол-во мест не может быть больше чем 10")
    private int place;

    @NotNull(message = "Выберите подходящее из списка")
    private BodyType type;

    public Car() {
    }

    public Car(String model, String marka, String color, int speed, int place, BodyType type) {
        this.model = model;
        this.marka = marka;
        this.color = color;
        this.speed = speed;
        this.place = place;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public BodyType getType() {
        return type;
    }

    public void setType(BodyType type) {
        this.type = type;
    }
}
