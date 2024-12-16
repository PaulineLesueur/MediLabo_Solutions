package com.openclassrooms.ms_diabetes_report.client;

import com.openclassrooms.ms_diabetes_report.DTO.PatientDTO;
import com.openclassrooms.ms_diabetes_report.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-api-gateway", url = "http://ms-gateway:8080", configuration = FeignClientConfig.class)
public interface PatientClient {
    @GetMapping("/patient/{id}")
    PatientDTO getPatientById(@PathVariable("id") int id);

}
