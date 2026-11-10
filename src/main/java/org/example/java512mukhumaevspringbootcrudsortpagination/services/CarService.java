package org.example.java512mukhumaevspringbootcrudsortpagination.services;

import net.datafaker.Faker;
import org.example.java512mukhumaevspringbootcrudsortpagination.enums.BodyType;
import org.example.java512mukhumaevspringbootcrudsortpagination.exception.CarNotFoundException;
import org.example.java512mukhumaevspringbootcrudsortpagination.models.Car;
import org.example.java512mukhumaevspringbootcrudsortpagination.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarService {
    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public Page<Car> search(String query, String searchField, Pageable pageable) {
        if (query == null || query.isBlank()) return repo.findAll(pageable);

        return switch(searchField) {
            case "model" -> repo.findByModelContainingIgnoreCase(query, pageable);
            case "marka" -> repo.findByMarkaContainingIgnoreCase(query, pageable);
            case "color" -> repo.findByColorContainingIgnoreCase(query, pageable);
            case "speed" -> repo.findBySpeedContaining(query, pageable);
            case "place" -> repo.findByPlaceContaining(query, pageable);
            case "type" -> repo.findByTypeContaining(query, pageable);
            default -> repo.searchAll(query, pageable);
        };
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

    public void clear() {
        repo.deleteAll();
    }
}