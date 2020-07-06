package com.course.practicaljava.common;

import org.springframework.stereotype.Component;

@Component
public class CarElasticDataSource {
//
//	@Autowired
//	private CarElasticRepository carRepository;
//
//	@Autowired
//	@Qualifier("randomCarService")
//	private CarService carService;
//
//	@Autowired
//	@Qualifier("webClientElasticsearch")
//	private WebClient webClient;
//
//	private static final Logger LOG = LoggerFactory.getLogger(CarElasticDataSource.class);
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void populateData() {
//		var body = webClient.delete().uri("/practical-java").retrieve().bodyToMono(String.class).block();
//		LOG.info("End delete with reposnse : {}", body);
//
//		var cars = new ArrayList<Car>();
//		for (int i = 0; i < 10000; i++) {
//			cars.add(carService.generateCar());
//		}
//
//		carRepository.saveAll(cars);
//
//		LOG.info("Saved {} cars", carRepository.count());
//	}
}
