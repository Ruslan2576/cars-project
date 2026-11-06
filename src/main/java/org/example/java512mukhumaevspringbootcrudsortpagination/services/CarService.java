package org.example.java512mukhumaevspringbootcrudsortpagination.services;

import net.datafaker.Faker;
import org.example.java512mukhumaevspringbootcrudsortpagination.enums.BodyType;
import org.example.java512mukhumaevspringbootcrudsortpagination.exception.CarNotFoundException;
import org.example.java512mukhumaevspringbootcrudsortpagination.models.Car;
import org.example.java512mukhumaevspringbootcrudsortpagination.repositories.CarRepository;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarService {
    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public List<Car> getCars(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).getContent();
    }

    public void save(Car car) {
        repo.save(car);
    }

    public void deleteCarById(Long id) {
        repo.deleteById(id);
    }

    public Car getCarById(Long id) {
        return repo.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public long getPagesCount(int size) {
        return (long) Math.ceil((double) getCarsCount() / size);
    }

    public void generate() {
        Faker faker = new Faker();
        List<Car> carList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Car car = new Car();
            car.setModel(faker.vehicle().model());
            car.setMarka(faker.vehicle().make());
            car.setColor(faker.color().name());
            car.setPlace(faker.number().numberBetween(2, 7));
            car.setSpeed(faker.number().numberBetween(200, 300));
            BodyType[] values = BodyType.values();
            car.setType(values[faker.number().numberBetween(0, values.length)]);

            carList.add(car);
        }

        repo.saveAll(carList);
    }

    public long getCarsCount() {
        return repo.count();
    }

    public void clear() {
        repo.deleteAll();
    }
}