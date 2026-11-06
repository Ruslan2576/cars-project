package org.example.java512mukhumaevspringbootcrudsortpagination.controllers;

import jakarta.validation.Valid;
import org.example.java512mukhumaevspringbootcrudsortpagination.enums.BodyType;
import org.example.java512mukhumaevspringbootcrudsortpagination.models.Car;
import org.example.java512mukhumaevspringbootcrudsortpagination.services.CarService;
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
    public String addAll(@RequestParam int page, @RequestParam int size) {
        service.generate();
        return "redirect:/car?page=" + page + "&size=" + size;
    }

    @GetMapping
    public String all(@RequestParam(defaultValue = "0", required = false) int page,
                      @RequestParam(defaultValue = "5", required = false) int size,
                      Model model) {

        long totalPages = service.getPagesCount(size);
        model.addAttribute("cars", service.getCars(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("totalCars", service.getCarsCount());
        return "car/index";
    }

    @GetMapping("/create")
    public String create(Model model, @RequestParam int page, @RequestParam int size) {
        model.addAttribute("car", new Car());
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "car/create";
    }

    @PostMapping("/add")
    public String add(@RequestParam int page,
                      @RequestParam int size,
                      @Valid Car car,
                      BindingResult br,
                      Model model) {

        if (br.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("bodyTypes", BodyType.values());
            model.addAttribute("car", car);
            return "car/create";
        }

        service.save(car);
        return "redirect:/car?page=" + page + "&size=" + size;
    }

    // Пришлось сделать post иначе page и size null
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam int page,
                       @RequestParam int size,
                       Model model) {

        model.addAttribute("car", service.getCarById(id));
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "car/edit";
    }

    @PutMapping("/update")
    public String update(@RequestParam int page,
                         @RequestParam int size,
                         @Valid Car car,
                         BindingResult br,
                         Model model) {

        if (br.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("bodyTypes", BodyType.values());
            return "car/edit";
        }

        service.save(car);
        return "redirect:/car?page=" + page + "&size=" + size;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        service.deleteCarById(id);
        return "redirect:/car?page=" + page + "&size=" + size;
    }


    @GetMapping("/clear")
    public String clear(@RequestParam int size) {
        service.clear();
        return "redirect:/car?size=" + size;
    }
}