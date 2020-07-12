package com.course.practicaljava.api.server;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.practicaljava.entity.Car;
import com.course.practicaljava.repository.CarElasticRepository;
import com.course.practicaljava.service.CarService;

@RequestMapping(value = "/api/car/v1")
@RestController
public class CarApi {

	private static final Logger LOG = LoggerFactory.getLogger(CarApi.class);

	@Autowired
	private CarElasticRepository carRepository;

	@Autowired
	private CarService carService;

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

	@GetMapping(value = "/count")
	public String countCar() {
		return "There are : " + carRepository.count() + " cars";
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveCar(@RequestBody Car car) {
		var id = carRepository.save(car).getId();

		return "Saved with ID : " + id;
	}

	@GetMapping(value = "/{id}")
	public Car getCar(@PathVariable("id") String carId) {
		return carRepository.findById(carId).orElse(null);
	}

	@PutMapping(value = "/{id}")
	public String updateCar(@PathVariable("id") String carId, @RequestBody Car updatedCar) {
		updatedCar.setId(carId);
		var newCar = carRepository.save(updatedCar);

		return "Updated car with ID : " + newCar.getId();
	}

	@GetMapping(value = "/find-json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findCarsByBrandAndColor(@RequestBody Car car, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		var pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "price"));
		return carRepository.findByBrandAndColor(car.getBrand(), car.getColor(), pageable).getContent();
	}

	@GetMapping(value = "/cars/{brand}/{color}")
	public List<Car> findCarsByPath(@PathVariable String brand, @PathVariable String color,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		var pageable = PageRequest.of(page, size);
		return carRepository.findByBrandAndColor(brand, color, pageable).getContent();
	}

	@GetMapping(value = "/cars")
	public List<Car> findCarsByParam(@RequestParam String brand, @RequestParam String color,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		var pageable = PageRequest.of(page, size);
		return carRepository.findByBrandAndColor(brand, color, pageable).getContent();
	}

	@GetMapping(value = "/cars/date")
	public List<Car> findCarsReleasedAfter(
			@RequestParam(name = "first_release_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstReleaseDate) {
		return carRepository.findByFirstReleaseDateAfter(firstReleaseDate);
	}

}
