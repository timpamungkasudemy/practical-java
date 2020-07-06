package com.course.practicaljava;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.course.practicaljava.entity.Car;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {

}
