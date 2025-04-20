package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.response.SuccessResponse;

@RestControllerAdvice
public class CustomExceptionhandler {
	@ExceptionHandler(value = EmployeeNotFoundException.class)
	public ResponseEntity<SuccessResponse> employeeNotFoundException(Exception exception) {
		return new ResponseEntity<>(new SuccessResponse(true, exception.getMessage(), null), HttpStatus.BAD_REQUEST);
	}

}
