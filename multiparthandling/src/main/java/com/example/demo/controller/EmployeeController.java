package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeLoginDto;
import com.example.demo.response.SuccessResponse;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/registerEmployee")

	public ResponseEntity<SuccessResponse> registerEmployee(@ModelAttribute EmployeeDto employeeDto)
			throws IOException {
		EmployeeDto registerEmployee = employeeService.registerEmployee(employeeDto);
		if (registerEmployee != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Successfull", registerEmployee), HttpStatus.OK);

		}
		return new ResponseEntity<>(new SuccessResponse(true, "Unsuccessfull", null), HttpStatus.BAD_REQUEST);

//		EmployeeDto registerEmployee = employeeService.registerEmployee(employeeDto);
//		if (registerEmployee != null) {
//			return ResponseEntity.status(200).body(registerEmployee);
//		}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not Added");
//	}

	}

	@GetMapping("/GetAll")
	public ResponseEntity<SuccessResponse> getAll() {
		List<EmployeeDto> all = employeeService.getAll();
		if (all.isEmpty()) {
			return new ResponseEntity<>(new SuccessResponse(true, "Not Fetched", null), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SuccessResponse(false, "Get  Successfully", all), HttpStatus.OK);

	}

	@GetMapping("/getEmployee/{employeeId}")
	public ResponseEntity<SuccessResponse> getEmployee(@PathVariable long employeeId) {
		EmployeeDto employee = employeeService.getEmployee(employeeId);
		if (employee != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Successfull", employee), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Success", null), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/getEmployeeByEmail")
	public ResponseEntity<SuccessResponse> getEmployeeByEmail(@RequestBody EmployeeLoginDto employeeLoginDto) {
		EmployeeDto employeeByEmail = employeeService.getEmployeeByEmail(employeeLoginDto);
		if (employeeByEmail != null) {

			return new ResponseEntity<>(new SuccessResponse(false, "Get Successfully", employeeByEmail), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Get", null), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/getEmployeeUsingEmail")
	public ResponseEntity<SuccessResponse> getEmployeeUsingEmail(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto employeeUsingEmail = employeeService.getEmployeeUsingEmail(employeeDto);
		if (employeeUsingEmail != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Get Successfully", employeeUsingEmail),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Getting Failed", null), HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/deleteById/{employeeId}")
	public ResponseEntity<SuccessResponse> deleteById(@PathVariable long employeeId) {
		EmployeeDto deleteById = employeeService.deleteById(employeeId);
		if (deleteById != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Deleted", deleteById), HttpStatus.OK);

		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Deleted", null), HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/deleteEmployeeByEmail/{emailId}")
	public ResponseEntity<SuccessResponse> deleteEmployeeByEmail(@PathVariable String emailId) {
		EmployeeDto deleteEmployeeByEmail = employeeService.deleteEmployeeByEmail(emailId);
		if (deleteEmployeeByEmail != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Deleted By Email", deleteEmployeeByEmail),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Deleted By Email", null), HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/deleteEmployeeByEmailId")
	public ResponseEntity<SuccessResponse> deleteEmployeeByEmailId(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto deleteEmployeeByEmailId = employeeService.deleteEmployeeByEmailId(employeeDto);
		if (deleteEmployeeByEmailId != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Deleted Successfully", deleteEmployeeByEmailId),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Not Deleted Successfully", null),
				HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/deleteEmployee/{employeeId}")
	public ResponseEntity<SuccessResponse> deleteEmployee(@PathVariable long employeeId) {
		employeeService.deleteEmployee(employeeId);

		return new ResponseEntity<>(new SuccessResponse(false, "Successfully Deleted", null), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{employeeId}")
	public ResponseEntity<SuccessResponse> delete(@PathVariable long employeeId) {
		String delete = employeeService.delete(employeeId);
		if (delete.isEmpty()) {
			return new ResponseEntity<>(new SuccessResponse(true, "Not Deleted", null), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SuccessResponse(false, "DELETED", delete), HttpStatus.OK);

	}

	@DeleteMapping("/deleteByEmail")
	public ResponseEntity<SuccessResponse> deleteByEmail(@RequestBody EmployeeLoginDto employeeLoginDto) {
		EmployeeDto deleteByEmail = employeeService.deleteByEmail(employeeLoginDto);
		if (deleteByEmail != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Deletion Done", deleteByEmail), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Deletion Failed", null), HttpStatus.BAD_REQUEST);

	}

	@GetMapping("getEmployeeByEmail/{emailId}")
	public ResponseEntity<SuccessResponse> getEmployeeByEmail(@PathVariable String emailId) {
		EmployeeDto employeeByEmailId = employeeService.getEmployeeByEmailId(emailId);
		if (employeeByEmailId != null) {
			return new ResponseEntity<>(new SuccessResponse(false, "Fetched Successfully", employeeByEmailId),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new SuccessResponse(true, "Failed", null), HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/deleteByValidate")
	public ResponseEntity<SuccessResponse> deleteByValidate(@RequestBody EmployeeLoginDto employeeLoginDto) {
		String deleteByValidate = employeeService.deleteByValidate(employeeLoginDto);
		if (deleteByValidate.isEmpty()) {
			return new ResponseEntity<>(new SuccessResponse(true, "Not Deleted", null), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new SuccessResponse(false, "Deletion Done", deleteByValidate), HttpStatus.OK);

	}
}
