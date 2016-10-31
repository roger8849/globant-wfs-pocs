package com.globant.sample.microservice;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Juan Krzemien
 */
@RepositoryRestResource
public interface SampleRepository extends PagingAndSortingRepository<Sample, Long> {
}
