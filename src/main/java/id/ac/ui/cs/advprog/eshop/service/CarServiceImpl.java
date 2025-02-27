package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create(Car car) {
        carRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> carsList = new ArrayList<>();
        carIterator.forEachRemaining(carsList::add);
        return carsList;
    }

    @Override
    public Optional<Car> findById(String carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Optional<Car> updateById(String carId, Car car) {
        return carRepository.update(carId, car);
    }

    @Override
    public void deleteById(String carId) {
        carRepository.delete(carId);
    }
}
