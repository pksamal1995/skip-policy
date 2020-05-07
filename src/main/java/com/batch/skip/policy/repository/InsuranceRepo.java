package com.batch.skip.policy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.batch.skip.policy.model.Insurance;

@Repository
public interface InsuranceRepo extends CrudRepository<Insurance, Long> {

}
