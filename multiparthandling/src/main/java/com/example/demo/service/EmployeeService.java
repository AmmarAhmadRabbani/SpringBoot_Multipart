package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;

public interface EmployeeService {
	public EmployeeDto registerEmployee(EmployeeDto employeeDto) throws IOException;

	public List<EmployeeDto> getAll();

	public EmployeeDto getEmployee(long employeeId);

//	Validate EmailId and Password to get Employee details
	public EmployeeDto getEmployeeByEmail(EmployeeLoginDto employeeLoginDto);

	public EmployeeDto getEmployeeUsingEmail(EmployeeDto employeeDto);

	public EmployeeDto getEmployeeByEmailId(String emailId);

	public EmployeeDto deleteById(long employeeId);

	public EmployeeDto deleteEmployeeByEmail(String emailId);

// Delete by Email id, if present
	public EmployeeDto deleteEmployeeByEmailId(EmployeeDto employeeDto);

	public void deleteEmployee(long employeeId);

	public String delete(long employeeId);

// Delete if email id is present
	public EmployeeDto deleteByEmail(EmployeeLoginDto employeeLoginDto);

//Deletion after the validation of email and password
	public String deleteByValidate(EmployeeLoginDto employeeLoginDto);

//	Validate EmailId and Password to update Employee
//	public EmployeeDto updateEmployeee(EmployeeDto employeeDto);

//	Validate EmailId and Password to update Multipart field
//	public String updateEmployeeFile();

}
