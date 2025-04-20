package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	private long employeeId;
	private String name;
	private String emailId;
	private String password;

	@JsonProperty(access = Access.WRITE_ONLY)
	private MultipartFile employeeDataFile;
	@JsonProperty(access = Access.READ_ONLY)
	private String filePath;
}
