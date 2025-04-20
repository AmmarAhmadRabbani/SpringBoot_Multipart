package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Value("${employee.fileDirectory}")
	private String uploadDir;

	@Override
	public EmployeeDto registerEmployee(EmployeeDto employeeDto) throws IOException {

		if (employeeDto != null) {

			String fileName = UUID.randomUUID() + "_"
					+ StringUtils.cleanPath(employeeDto.getEmployeeDataFile().getOriginalFilename());
			Path path = Path.of(uploadDir);
			if (Files.notExists(path)) {
				Files.createDirectories(path);
			}

			Path targetPath = Path.of(uploadDir, fileName);

			Files.copy(employeeDto.getEmployeeDataFile().getInputStream(), targetPath,
					StandardCopyOption.REPLACE_EXISTING);

			Employee employee = new Employee();
			BeanUtils.copyProperties(employeeDto, employee);

			employee.setEmployeeDataFile(fileName);
			employeeRepository.save(employee);
			BeanUtils.copyProperties(employee, employeeDto, "employeeDataFile");
			employeeDto.setFilePath(path.toString());
			return employeeDto;
		}
		throw new RuntimeException("Not Registered");
	}

	@Override
	public List<EmployeeDto> getAll() {
		ArrayList<EmployeeDto> listEmployeeDtos = new ArrayList<>();
		List<Employee> allEmployees = employeeRepository.findAll();
		if (allEmployees.isEmpty()) {
			throw new RuntimeException("Employees Not Availale");
		}
		allEmployees.forEach(i -> {
			String employeeDataFile = i.getEmployeeDataFile();
			EmployeeDto employeeDto = new EmployeeDto();
			BeanUtils.copyProperties(i, employeeDto);
			employeeDto.setFilePath(employeeDataFile);
			listEmployeeDtos.add(employeeDto);
		});
		return listEmployeeDtos;
	}

	@Override
	public EmployeeDto getEmployee(long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Invalid Employee ID"));
		String employeeDataFile = employee.getEmployeeDataFile();
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employee, employeeDto);
		employeeDto.setFilePath(employeeDataFile);
		return employeeDto;
	}

	@Override
	public EmployeeDto getEmployeeByEmail(EmployeeLoginDto employeeLoginDto) {
		Optional<Employee> byEmailId = employeeRepository.findByEmailId(employeeLoginDto.getEmailId());
		if (byEmailId.isEmpty()) {
			throw new EmployeeNotFoundException("Invalid Email");
		}
		Employee employee = byEmailId.get();
		String password = employee.getPassword();
		if (!employeeLoginDto.getPassword().equals(password)) {
			throw new EmployeeNotFoundException("Wrong Password");
		}
		String employeeDataFile = employee.getEmployeeDataFile();
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employee, employeeDto);
		employeeDto.setFilePath(employeeDataFile);
		return employeeDto;
	}

	@Override
	public EmployeeDto getEmployeeUsingEmail(EmployeeDto employeeDto) {
		Optional<Employee> byEmailId = employeeRepository.findByEmailId(employeeDto.getEmailId());
		if (!byEmailId.isPresent()) {
			throw new EmployeeNotFoundException("Inavalid Email Id");
		}
		Employee employee = byEmailId.get();
		String password = employee.getPassword();
		if (!employeeDto.getPassword().equals(password)) {
			throw new EmployeeNotFoundException("Wrong Password");
		}
		String employeeDataFile = employee.getEmployeeDataFile();
		BeanUtils.copyProperties(employee, employeeDto);
		employeeDto.setFilePath(employeeDataFile);
		return employeeDto;
	}

	@Override
	public EmployeeDto deleteById(long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Invalid EmployeeId"));
		employeeRepository.deleteById(employeeId);
		return new EmployeeDto();
	}

	@Override
	public void deleteEmployee(long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Id Not Found"));
		employeeRepository.deleteById(employeeId);

	}

	@Override
	public String delete(long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Invalid Id"));
		employeeRepository.deleteById(employeeId);
		return "Deleted Successfully";
	}

	@Override
	public EmployeeDto getEmployeeByEmailId(String emailId) {
		Optional<Employee> byEmailId = employeeRepository.findByEmailId(emailId);
		if (byEmailId.isEmpty()) {
			throw new EmployeeNotFoundException("Email Id Unavailable");
		}
		Employee employee = byEmailId.get();
		String employeeDataFile = employee.getEmployeeDataFile();
		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employee, employeeDto);
		employeeDto.setFilePath(employeeDataFile);
		return employeeDto;
	}

	@Override
	public EmployeeDto deleteByEmail(EmployeeLoginDto employeeLoginDto) {
		Optional<Employee> byEmailId = employeeRepository.findByEmailId(employeeLoginDto.getEmailId());
		if (byEmailId.isPresent()) {
			Employee employee = byEmailId.get();
			employeeRepository.delete(employee);
			return new EmployeeDto();
		}

		throw new EmployeeNotFoundException("Email id invalid");
	}

	@Override
	public EmployeeDto deleteEmployeeByEmail(String emailId) {

		Optional<Employee> byEmailId = employeeRepository.findByEmailId(emailId);
		if (byEmailId.isPresent()) {
			Employee employee = byEmailId.get();
			employeeRepository.delete(employee);
			return new EmployeeDto();
		}
		throw new EmployeeNotFoundException("Email Not Present");

	}

	@Override
	public EmployeeDto deleteEmployeeByEmailId(EmployeeDto employeeDto) {
		Optional<Employee> byEmailId = employeeRepository.findByEmailId(employeeDto.getEmailId());
		if (byEmailId.isEmpty()) {
			throw new EmployeeNotFoundException("Invalid Email");
		}
		Employee employee = byEmailId.get();
		employeeRepository.delete(employee);
		return new EmployeeDto();
	}

	@Override
	public String deleteByValidate(EmployeeLoginDto employeeLoginDto) {
		Optional<Employee> byEmailId = employeeRepository.findByEmailId(employeeLoginDto.getEmailId());
		if (byEmailId.isEmpty()) {
			throw new EmployeeNotFoundException("Employee Email not Valid");
		}
		Employee employee = byEmailId.get();
		String password = employee.getPassword();
		if (!employeeLoginDto.getPassword().equals(password)) {
			throw new EmployeeNotFoundException("Entered incorrect password");
		}
		employeeRepository.delete(employee);
		return "Deleted Succesfully";
	}

}
