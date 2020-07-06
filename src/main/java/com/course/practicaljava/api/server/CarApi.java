package com.course.practicaljava.api.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.practicaljava.CarElasticRepository;
import com.course.practicaljava.entity.Car;
import com.course.practicaljava.service.CarService;

@RequestMapping(value = "/api/car/v1")
@RestController
public class CarApi {

	private static final Logger LOG = LoggerFactory.getLogger(CarApi.class);

	@Autowired
	private CarService carService;

	@Autowired
	private CarElasticRepository carRepository;

	@GetMapping(value = "/count")
	public String countCar() {
		return "There are : " + carRepository.count() + " cars";
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveCar(@RequestBody Car car) {
		var id = carRepository.save(car);

		return "Saved with ID : " + id;
	}

	@GetMapping(value = "/{id}")
	public Car getCar(@PathVariable("id") String carId) {
		return carRepository.findById(carId).orElse(null);
	}

	@PutMapping(value = "/{id}")
	public String updateCar(@PathVariable("id") String id, @RequestBody Car updatedCar) {
		updatedCar.setId(id);
		carRepository.save(updatedCar);

		return "Updated car with ID : " + id;
	}

	@GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
	public Car random() {
		return carService.generateCar();
	}

	@PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String echo(@RequestBody Car car) {
		LOG.info("Car is {}", car);

		return car.toString();
	}

	@GetMapping(value = "/random-cars", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> randomCars() {
		var result = new ArrayList<Car>();

		for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 10); i++) {
			result.add(carService.generateCar());
		}

		return result;
	}

}
