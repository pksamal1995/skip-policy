package com.batch.skip.policy.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.batch.skip.policy.model.Insurance;
import com.batch.skip.policy.repository.InsuranceRepo;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("${csvfile}")
	private String file;

	@Autowired
	private InsuranceRepo insuranceRepo;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("CSV ---> DB JOB").incrementer(new RunIdIncrementer()).start(step()).build();

	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("Insurance file lOAD").<Insurance, Insurance>chunk(1).reader(itemReader())
				.writer(itemWriter()).faultTolerant().skipPolicy(skipPolicy()).build();
	}

	@Bean
	public SkipPolicy skipPolicy() {

		return (Throwable throwable, int skipCount) -> {
			return (skipCount > 5) ? false : true;
		};
	}

	@Bean
	public ItemWriter<Insurance> itemWriter() {

		return (insurances) -> {
			insuranceRepo.saveAll(insurances);
		};
	}

	@Bean
	public FlatFileItemReader<Insurance> itemReader() {

		FlatFileItemReader<Insurance> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource(file));
		flatFileItemReader.setName("csv-reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<Insurance> lineMapper() {
		DefaultLineMapper<Insurance> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(
				new String[] { "policy_id", "statecode", "county", "line", "construction", "point_granularity" });

		BeanWrapperFieldSetMapper<Insurance> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Insurance.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return defaultLineMapper;
	}

}
