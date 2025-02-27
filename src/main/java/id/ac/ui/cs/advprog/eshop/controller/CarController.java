package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.service.LogService;
import id.ac.ui.cs.advprog.eshop.service.LogServiceConsole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final LogService logService;

    public CarController(CarService carService, LogServiceConsole logService) {
        this.carService = carService;
        this.logService = logService;
    }

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        logService.info("Navigating to Create Car Page");
        Car car = new Car();
        model.addAttribute("car", car);
        return "CreateCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        logService.info("Creating new car: " + car);
        carService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        logService.info("Fetching car list");
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "CarList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        logService.info("Opening Edit Car Page for carId: " + carId);

        Optional<Car> car = carService.findById(carId);

        if (car.isPresent()) {
            model.addAttribute("car", car.get());
            return "EditCar";
        } else {
            logService.warn("Car not found for Id: " + carId);
            return "redirect:../listCar";
        }
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        logService.info("Updating car with Id: " + car.getCarId());
        carService.updateById(car.getCarId(), car);
        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        logService.info("Deleting car with Id: " + carId);
        carService.deleteById(carId);
        return "redirect:listCar";
    }
}
