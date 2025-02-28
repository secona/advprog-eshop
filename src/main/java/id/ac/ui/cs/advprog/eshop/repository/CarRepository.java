package id.ac.ui.cs.advprog.eshop.repository;

import java.util.*;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

@Repository
public class CarRepository implements WritableRepository<Car, String>, ReadonlyRepository<Car, String> {
    static int id = 0;

    private List<Car> carData = new ArrayList<>();

    public Car create(Car car) {
        if (car.getCarId() == null) {
            UUID uuid = UUID.randomUUID();
            car.setCarId(uuid.toString());
        }

        carData.add(car);
        return car;
    }

    public Optional<Car> findById(String id) {
        for (Car car : carData) {
            if (car.getCarId().equals(id)) {
                return Optional.of(car);
            }
        }

        return Optional.empty();
    }

    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    public Optional<Car> update(String id, Car updatedCar) {
        for (int i = 0; i < carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getCarId().equals(id)) {
                car.setCarName(updatedCar.getCarName());
                car.setCarColor(updatedCar.getCarColor());
                car.setCarQuantity(updatedCar.getCarQuantity());
                return Optional.of(car);
            }
        }

        return Optional.empty();
    }

    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}
