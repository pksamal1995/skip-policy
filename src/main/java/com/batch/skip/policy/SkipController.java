package com.batch.skip.policy;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch.skip.policy.model.Insurance;
import com.batch.skip.policy.service.CsvBatchLoadService;

@RestController
@RequestMapping("/batch")
public class SkipController {

	@Autowired
	private CsvBatchLoadService service;
	
	@GetMapping("skip")
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		return service.loadCsv();
	}
	
	@GetMapping("/getall")
	public List<Insurance> getAll(){
		return (List<Insurance>) service.getAllInsurances();
	}
}
