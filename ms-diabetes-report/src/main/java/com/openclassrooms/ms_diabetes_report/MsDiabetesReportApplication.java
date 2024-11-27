package com.openclassrooms.ms_diabetes_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDiabetesReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDiabetesReportApplication.class, args);
	}

}
