package org.example.java512mukhumaevspringbootcrudsortpagination.controllers;

import jakarta.validation.Valid;
import org.example.java512mukhumaevspringbootcrudsortpagination.enums.BodyType;
import org.example.java512mukhumaevspringbootcrudsortpagination.models.Car;
import org.example.java512mukhumaevspringbootcrudsortpagination.services.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/add-all")
    public String addAll(@RequestParam String sortBy, @RequestParam int page, @RequestParam int size) {
        service.generate();
        return "redirect:/car?sortBy=" + sortBy + "&page=" + page + "&size=" + size;
    }

    @GetMapping
    public String getCars(@RequestParam(defaultValue = "") String query,
                          @RequestParam(defaultValue = "all") String searchField,
                          @RequestParam(defaultValue = "id", required = false) String sortBy,
                          @RequestParam(defaultValue = "0", required = false) int page,
                          @RequestParam(defaultValue = "5", required = false) int size,
                          Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Car> carPage = service.search(query, searchField, pageable);

        model.addAttribute("cars", carPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("query", query);
        model.addAttribute("searchField", searchField);
        model.addAttribute("totalPages", carPage.getTotalPages());
        model.addAttribute("totalCars", carPage.getTotalElements());

        return "car/index";
    }

    @GetMapping("/create")
    public String create(Model model, @RequestParam String sortBy, @RequestParam int page, @RequestParam int size) {
        model.addAttribute("car", new Car());
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        return "car/create";
    }

    @PostMapping("/add")
    public String add(@RequestParam String sortBy, @RequestParam int page,
                      @RequestParam int size, @Valid Car car,
                      BindingResult br, Model model) {

        if (br.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("bodyTypes", BodyType.values());
            model.addAttribute("car", car);
            return "car/create";
        }

        service.save(car);
        return "redirect:/car?sortBy=" + sortBy + "&page=" + page + "&size=" + size;

    }

    // Пришлось сделать post иначе page и size null
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @RequestParam String sortBy,
                       @RequestParam int page, @RequestParam int size,
                       Model model) {

        model.addAttribute("car", service.getCarById(id));
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        return "car/edit";
    }

    @PutMapping("/update")
    public String update(@RequestParam String sortBy, @RequestParam int page,
                         @RequestParam int size, @Valid Car car,
                         BindingResult br, Model model) {

        if (br.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("bodyTypes", BodyType.values());
            return "car/edit";
        }

        service.save(car);
        return "redirect:/car?sortBy=" + sortBy + "&page=" + page + "&size=" + size;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @RequestParam String sortBy,
                         @RequestParam int page, @RequestParam int size) {
        service.deleteCarById(id);
        return "redirect:/car?sortBy=" + sortBy + "&page=" + page + "&size=" + size;
    }

    @GetMapping("/clear")
    public String clear(@RequestParam int size) {
        service.clear();
        return "redirect:/car?size=" + size;
    }
}
